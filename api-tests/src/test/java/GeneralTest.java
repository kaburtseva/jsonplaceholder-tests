import com.jsonpl.api.ProjectConfig;
import com.jsonpl.api.models.Users;
import com.jsonpl.api.services.CommentsService;
import com.jsonpl.api.services.PostService;
import com.jsonpl.api.services.UsersService;
import io.qameta.allure.Issue;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.AssertDelegateTarget;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class GeneralTest {

    PostService postService = new PostService();
    CommentsService commentsService = new CommentsService();

    @BeforeClass
    public void setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
    }

    @DataProvider(name = "ids")
    public Object[][] createData() {
        return new Object[][]{{777}, {0}};
    }

    @DataProvider(name = "invalidIds")
    public Object[][] createDataForInvalidUserIds() {
        return new Object[][]{{111}, {000}};
    }

    @DataProvider(name = "postIds")
    public Object[][] createDataForPostIds() {
        return new Object[][]{{4, 200}, {111, 404}};
    }

    @DataProvider(name = "postAndUserIds")
    public Object[][] createDataForPostIdsAndUsers() {
        return new Object[][]{{1, 4, "xxx", 200}, {1, 101, "testTwo", 500}};
    }

    @Test(description = "Verify that no posts should be displayed for unknown user with id {id}", dataProvider = "invalidIds")
    public void getPostByInvalidId(int id) {
        Assert.assertTrue(postService.getAllPostsByUser(id).isEmpty(),
                "Some posts exist for user with invalid id");
    }

    @Test(description = "Verify that there is no comments should be displayed for unknown post id {id}", dataProvider = "ids")
    public void getCommentByInvalidId(int id) {
        Assert.assertTrue(commentsService.getAllCommentsForThePost(id).isEmpty(),
                "Some comments exist for post with invalid id");
    }

    @Test(description = "Verify that all post ids are unique")
    public void getAllUniquesId() {
        UsersService userApiService = new UsersService();
        Users user = userApiService.findUserIdByName("Delphine");
        List<Integer> post = postService.getAllPostsByUser(user.id)
                .stream().map(x -> x.id).collect(Collectors.toList());
        Assert.assertEquals(post.size(), post.stream().distinct().count(), "Post ids aren't unique");
    }

    @Test(description = "Verify that all user ids are unique")
    public void getAllUserIds() {
        UsersService userApiService = new UsersService();
        List<Integer> userId = userApiService.getAllUsers().stream().map(it -> it.id).collect(Collectors.toList());
        Assert.assertEquals(userId.size(), userId.stream().distinct().count(), "User ids aren't unique");
    }

    @Test(description = "Verify that new post can be created")
    @Issue("new post created only for userId 0 even passed userId is 1." +
            "Not existed in the list of post for user 1 and user 0. Status code is 201")
    public void createNewPost() {
        int postId = postService.createNewPost(1).id;
        long countOfPostWithId = postService.getAllPostsByUser(1)
                .stream().filter(i -> i.id == postId).count();
        Assert.assertTrue(countOfPostWithId > 0, String.format("Post with id %s isn't created", postId));
    }


    @Test(description = "Verify that post can be deleted", dataProvider = "postIds")
    @Issue("Posts can not be deleted properly from the list, however status always 200")
    public void deletePost(int postId, int statusCode) {
        Assert.assertEquals(postService.deletePostById(postId), statusCode);
        long allPostsWithProvidedId = postService.getAllPosts().stream().filter(i -> i.id == postId).count();
        Assert.assertEquals(allPostsWithProvidedId, 0, String.format("Post with id %s still displayed in list", postId));
    }

    @Test(description = "Update existing post with {postId} for user {userId} to new title {title}", dataProvider = "postAndUserIds")
    @Issue("Posts can not be updated properly")
    public void updateDefault(int userId, int postId, String updatedTitle, int statusCode) {
        Assert.assertEquals(postService.updatePostById(postId, userId, updatedTitle), statusCode);
        if (statusCode != 500) {
            long allPostsWithNewTitle = postService.getAllPosts().stream().filter(i -> i.title().contains(updatedTitle)).count();
            Assert.assertTrue(allPostsWithNewTitle > 0, String.format("Post with title %s not displayed in list", updatedTitle));
        }
    }
}

