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
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class EmailTest {

    UsersService apiService = new UsersService();
    PostService postService = new PostService();
    CommentsService commentsService = new CommentsService();
    EmailDomainService emailDomainService = new EmailDomainService();
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
    }

    @DataProvider(name = "username")
    public Object[][] createData() {
        return new Object[][]{{"Delphine"}, {"Kamren"}};
    }

    @Test(description = "Verify if email address has correct format for username {username}", dataProvider = "username")
    void verifyEmailFormat(String username) {
        Users user = apiService.findUserIdByName(username);
        List<Posts> posts = postService.getAllPostsByUser(String.valueOf(user.id));
        Assert.assertTrue(!posts.isEmpty(), "No posts for current user");
        posts.forEach(post ->
                commentsService.getAllCommentsForThePost(String.valueOf(post.id))
                        .forEach(comment ->
                                Assert.assertTrue(emailDomainService.isEmailHasCorrectFormat(comment.email),
                                        comment.email + "has invalid format")
                        ));

    }

    @Test(description = "Verify if email address has correct format for username {username}: with valid domains", dataProvider = "username")
    @Issue("Not all domains are reachable")
    public void verifyEmailDomains(String username) {
        Users user = apiService.findUserIdByName("Delphine");
        postService.getAllPostsByUser(String.valueOf(user.id)).forEach(
                post -> commentsService.getAllEmailAddresses(String.valueOf(post.id)).forEach(
                        email ->
                                softAssert.assertEquals(emailDomainService.verifyDomainEmail(email), "200",
                                        email + "email has unreachable domain based for post with id " + post.id)
                )

        );
        softAssert.assertAll();
    }
}
