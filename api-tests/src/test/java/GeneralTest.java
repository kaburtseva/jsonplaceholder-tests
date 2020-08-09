import com.jsonpl.api.ProjectConfig;
import com.jsonpl.api.models.Posts;
import com.jsonpl.api.models.Users;
import com.jsonpl.api.services.CommentsService;
import com.jsonpl.api.services.PostService;
import com.jsonpl.api.services.UsersService;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class GeneralTest {

    UsersService userApiService = new UsersService();
    PostService postService = new PostService();
    CommentsService commentsService = new CommentsService();

    @BeforeClass
    public void setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
    }

    @DataProvider(name = "ids")
    public Object[][] createData() {
        return new Object[][]{{"test"}, {"0"}};
    }

    @Test(description = "Verify that no posts should be displayed for unknown user with id {id}", dataProvider = "ids")
    public void getPostByInvalidId(String id) {
        Assert.assertTrue(postService.getAllPostsByUser(id).isEmpty(),
                "Some posts exist for user with invalid id");
    }

    @Test(description = "Verify that there is no comments should be displayed for unknown post id {id}", dataProvider = "ids")
    public void getCommentByInvalidId(String id) {
        Assert.assertTrue(commentsService.getAllCommentsForThePost(id).isEmpty(),
                "Some comments exist for post with invalid id");
    }

    @Test(description = "Verify that all post ids are unique")
    public void getAllUniquesId() {
        UsersService userApiService = new UsersService();
        Users user = userApiService.findUserIdByName("Delphine");
        List<Integer> post = postService.getAllPostsByUser(String.valueOf(user.id))
                .stream().map(x -> x.id).collect(Collectors.toList());
       Assert.assertEquals(post.size(), post.stream().distinct().count(),"Post ids aren't unique");
    }
}

