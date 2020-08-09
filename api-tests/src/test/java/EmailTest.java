import com.jsonpl.api.ProjectConfig;
import com.jsonpl.api.models.Posts;
import com.jsonpl.api.models.Users;
import com.jsonpl.api.services.CommentsService;
import com.jsonpl.api.services.EmailDomainService;
import com.jsonpl.api.services.PostService;
import com.jsonpl.api.services.UsersService;
import io.qameta.allure.Issue;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class EmailTest {

    UsersService apiService = new UsersService();
    PostService postService = new PostService();
    CommentsService commentsService = new CommentsService();
    EmailDomainService emailDomainService = new EmailDomainService();
    private SoftAssertions softAssertions = new SoftAssertions();

    @BeforeClass
    public void setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
    }

    @DataProvider(name = "username")
    public Object[][] createData() {
        return new Object[][]{{"Delphine"}, {"Kamren"}};
    }

    @Test(description = "Verify if email address has correct format for username {userName}", dataProvider = "username")
    void verifyEmailFormat(String userName) {
        Users user = apiService.findUserIdByName(userName);
        List<Posts> posts = postService.getAllPostsByUser(String.valueOf(user.id));
        Assert.assertTrue(!posts.isEmpty(), "No posts for current user");
        posts.forEach(post ->
                commentsService.getAllCommentsForThePost(String.valueOf(post.id))
                        .forEach(comment ->
                                Assert.assertTrue(emailDomainService.isEmailHasCorrectFormat(comment.email),
                                        comment.email + "has invalid format")
                        ));

    }

    @Test(description = "Verify if email address has correct format: with valid domains")
    @Issue("Not all domains are reachable")
    public void verifyEmailDomains() {
        Users user = apiService.findUserIdByName("Delphine");
        postService.getAllPostsByUser(String.valueOf(user.id)).forEach(
                post -> commentsService.getAllEmailAddresses(String.valueOf(post.id)).forEach(
                        email ->
                                softAssertions.assertThat(emailDomainService.verifyDomainEmail(email)).contains("200")
                                        .withFailMessage("email has unreachable domain based for post with id " + post.id)
                )
        );

        softAssertions.assertAll();
    }
}
