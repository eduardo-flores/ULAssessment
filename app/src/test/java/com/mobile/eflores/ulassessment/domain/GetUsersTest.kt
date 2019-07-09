package com.mobile.eflores.ulassessment.domain

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobile.eflores.ulassessment.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.Exception
import java.lang.IllegalStateException

@RunWith(JUnit4::class)
class GetUsersTest {

    @MockK
    lateinit var userRepository: UserRepository

    lateinit var getUsers: GetUsers

    val gson = Gson()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.getUsers = GetUsers(userRepository)
    }

    @Test
    fun testFetchUsers_Positive() = runBlocking {
        var jsonString =
            "[{\"id\":1,\"name\":\"Leanne Graham\",\"username\":\"Bret\",\"email\":\"Sincere@april.biz\",\"address\":{\"street\":\"Kulas Light\",\"suite\":\"Apt. 556\",\"city\":\"Gwenborough\",\"zipcode\":\"92998-3874\",\"geo\":{\"lat\":\"-37.3159\",\"lng\":\"81.1496\"}},\"phone\":\"1-770-736-8031 x56442\",\"website\":\"hildegard.org\",\"company\":{\"name\":\"Romaguera-Crona\",\"catchPhrase\":\"Multi-layered client-server neural-net\",\"bs\":\"harness real-time e-markets\"}},{\"id\":2,\"name\":\"Ervin Howell\",\"username\":\"Antonette\",\"email\":\"Shanna@melissa.tv\",\"address\":{\"street\":\"Victor Plains\",\"suite\":\"Suite 879\",\"city\":\"Wisokyburgh\",\"zipcode\":\"90566-7771\",\"geo\":{\"lat\":\"-43.9509\",\"lng\":\"-34.4618\"}},\"phone\":\"010-692-6593 x09125\",\"website\":\"anastasia.net\",\"company\":{\"name\":\"Deckow-Crist\",\"catchPhrase\":\"Proactive didactic contingency\",\"bs\":\"synergize scalable supply-chains\"}},{\"id\":3,\"name\":\"Clementine Bauch\",\"username\":\"Samantha\",\"email\":\"Nathan@yesenia.net\",\"address\":{\"street\":\"Douglas Extension\",\"suite\":\"Suite 847\",\"city\":\"McKenziehaven\",\"zipcode\":\"59590-4157\",\"geo\":{\"lat\":\"-68.6102\",\"lng\":\"-47.0653\"}},\"phone\":\"1-463-123-4447\",\"website\":\"ramiro.info\",\"company\":{\"name\":\"Romaguera-Jacobson\",\"catchPhrase\":\"Face to face bifurcated interface\",\"bs\":\"e-enable strategic applications\"}},{\"id\":4,\"name\":\"Patricia Lebsack\",\"username\":\"Karianne\",\"email\":\"Julianne.OConner@kory.org\",\"address\":{\"street\":\"Hoeger Mall\",\"suite\":\"Apt. 692\",\"city\":\"South Elvis\",\"zipcode\":\"53919-4257\",\"geo\":{\"lat\":\"29.4572\",\"lng\":\"-164.2990\"}},\"phone\":\"493-170-9623 x156\",\"website\":\"kale.biz\",\"company\":{\"name\":\"Robel-Corkery\",\"catchPhrase\":\"Multi-tiered zero tolerance productivity\",\"bs\":\"transition cutting-edge web services\"}}]";
        val listUserType = object : TypeToken<List<User>>() {}.type
        val listUser = Gson().fromJson<List<User>>(jsonString, listUserType)

        coEvery { userRepository.fetchUsers() } returns listUser

        val list = getUsers.executeUser()

        assertNotNull(list)
        assert(list[0] is User)
        assertEquals(1, list[0].id)
        assertEquals("Leanne Graham", list[0].name)
        assertEquals(2, list[1].id)
        assertEquals("Antonette", list[1].username)
    }

    @Test
    fun testFetchUsers_Negative() = runBlocking {
        coEvery { userRepository.fetchUsers() } throws IllegalStateException("err")
        try {
            val list = getUsers.executeUser()
        } catch (e: Exception) {
            assert(e is IllegalStateException)
        }
    }

    @Test
    fun testFetchPosts_Positive() = runBlocking {
        var jsonString =
            "[{\"userId\":1,\"id\":1,\"title\":\"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\"body\":\"quia et suscipit\r\nsuscipit recusandae consequuntur expedita et cum\r\nreprehenderit molestiae ut ut quas totam\r\nnostrum rerum est autem sunt rem eveniet architecto\"},{\"userId\":1,\"id\":2,\"title\":\"qui est esse\",\"body\":\"est rerum tempore vitae\r\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\r\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\r\nqui aperiam non debitis possimus qui neque nisi nulla\"},{\"userId\":1,\"id\":3,\"title\":\"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\"body\":\"et iusto sed quo iure\r\nvoluptatem occaecati omnis eligendi aut ad\r\nvoluptatem doloribus vel accusantium quis pariatur\r\nmolestiae porro eius odio et labore et velit aut\"},{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\r\nsit amet autem assumenda provident rerum culpa\r\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\r\nquis sunt voluptatem rerum illo velit\"},{\"userId\":1,\"id\":5,\"title\":\"nesciunt quas odio\",\"body\":\"repudiandae veniam quaerat sunt sed\r\nalias aut fugiat sit autem sed est\r\nvoluptatem omnis possimus esse voluptatibus quis\r\nest aut tenetur dolor neque\"},{\"userId\":1,\"id\":6,\"title\":\"dolorem eum magni eos aperiam quia\",\"body\":\"ut aspernatur corporis harum nihil quis provident sequi\r\nmollitia nobis aliquid molestiae\r\nperspiciatis et ea nemo ab reprehenderit accusantium quas\r\nvoluptate dolores velit et doloremque molestiae\"},{\"userId\":1,\"id\":7,\"title\":\"magnam facilis autem\",\"body\":\"dolore placeat quibusdam ea quo vitae\r\nmagni quis enim qui quis quo nemo aut saepe\r\nquidem repellat excepturi ut quia\r\nsunt ut sequi eos ea sed quas\"},{\"userId\":1,\"id\":8,\"title\":\"dolorem dolore est ipsam\",\"body\":\"dignissimos aperiam dolorem qui eum\r\nfacilis quibusdam animi sint suscipit qui sint possimus cum\r\nquaerat magni maiores excepturi\r\nipsam ut commodi dolor voluptatum modi aut vitae\"},{\"userId\":1,\"id\":9,\"title\":\"nesciunt iure omnis dolorem tempora et accusantium\",\"body\":\"consectetur animi nesciunt iure dolore\r\nenim quia ad\r\nveniam autem ut quam aut nobis\r\net est aut quod aut provident voluptas autem voluptas\"},{\"userId\":1,\"id\":10,\"title\":\"optio molestias id quia eum\",\"body\":\"quo et expedita modi cum officia vel magni\r\ndoloribus qui repudiandae\r\nvero nisi sit\r\nquos veniam quod sed accusamus veritatis error\"}]";
        val listPostType = object : TypeToken<List<Post>>() {}.type
        val listPost = Gson().fromJson<List<Post>>(jsonString, listPostType)

        coEvery { userRepository.fetchPosts(any()) } returns listPost

        val list = getUsers.executePost(1)

        assertNotNull(list)
        assert(list[0] is Post)
        assertEquals(1, list[0].userId)
        assertEquals(1, list[0].id)
        assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", list[0].title)
        assertEquals(1, list[1].userId)
        assertEquals(2, list[1].id)
        assertEquals("qui est esse", list[1].title)
    }

    @Test
    fun testFetchPosts_Negative() = runBlocking {
        coEvery { userRepository.fetchPosts(1) } throws IllegalStateException("err")
        try {
            val list = getUsers.executePost(1)
        } catch (e: Exception) {
            assert(e is IllegalStateException)
        }
    }

    @Test
    fun testFetchComments_Positive() = runBlocking {
        var jsonString =
            "[{\"postId\":1,\"id\":1,\"name\":\"id labore ex et quam laborum\",\"email\":\"Eliseo@gardner.biz\",\"body\":\"laudantium enim quasi est quidem magnam voluptate ipsam eos\r\ntempora quo necessitatibus\r\ndolor quam autem quasi\r\nreiciendis et nam sapiente accusantium\"},{\"postId\":1,\"id\":2,\"name\":\"quo vero reiciendis velit similique earum\",\"email\":\"Jayne_Kuhic@sydney.com\",\"body\":\"est natus enim nihil est dolore omnis voluptatem numquam\r\net omnis occaecati quod ullam at\r\nvoluptatem error expedita pariatur\r\nnihil sint nostrum voluptatem reiciendis et\"},{\"postId\":1,\"id\":3,\"name\":\"odio adipisci rerum aut animi\",\"email\":\"Nikita@garfield.biz\",\"body\":\"quia molestiae reprehenderit quasi aspernatur\r\naut expedita occaecati aliquam eveniet laudantium\r\nomnis quibusdam delectus saepe quia accusamus maiores nam est\r\ncum et ducimus et vero voluptates excepturi deleniti ratione\"},{\"postId\":1,\"id\":4,\"name\":\"alias odio sit\",\"email\":\"Lew@alysha.tv\",\"body\":\"non et atque\r\noccaecati deserunt quas accusantium unde odit nobis qui voluptatem\r\nquia voluptas consequuntur itaque dolor\r\net qui rerum deleniti ut occaecati\"},{\"postId\":1,\"id\":5,\"name\":\"vero eaque aliquid doloribus et culpa\",\"email\":\"Hayden@althea.biz\",\"body\":\"harum non quasi et ratione\r\ntempore iure ex voluptates in ratione\r\nharum architecto fugit inventore cupiditate\r\nvoluptates magni quo et\"}]";
        val listCommentType = object : TypeToken<List<Comment>>() {}.type
        val listComment = Gson().fromJson<List<Comment>>(jsonString, listCommentType)

        coEvery { userRepository.fetchComments(any()) } returns listComment

        val list = getUsers.executeComment(1)

        assertNotNull(list)
        assert(list[0] is Comment)
        assertEquals(1, list[0].postId)
        assertEquals(1, list[0].id)
        assertEquals("id labore ex et quam laborum", list[0].name)
        assertEquals(1, list[1].postId)
        assertEquals(2, list[1].id)
        assertEquals("Jayne_Kuhic@sydney.com", list[1].email)
    }

    @Test
    fun testFetchComments_Negative() = runBlocking {
        coEvery { userRepository.fetchComments(1) } throws IllegalStateException("err")
        try {
            val list = getUsers.executeComment(1)
        } catch (e: Exception) {
            assert(e is IllegalStateException)
        }
    }

    @Test
    fun testFetchAlbums_Positive() = runBlocking {
        var jsonString =
            "[{\"userId\":1,\"id\":1,\"title\":\"quidem molestiae enim\"},{\"userId\":1,\"id\":2,\"title\":\"sunt qui excepturi placeat culpa\"},{\"userId\":1,\"id\":3,\"title\":\"omnis laborum odio\"}]";
        val listAlbumType = object : TypeToken<List<Album>>() {}.type
        val listAlbum = Gson().fromJson<List<Album>>(jsonString, listAlbumType)

        coEvery { userRepository.fetchAlbums(any()) } returns listAlbum

        val list = getUsers.executeAlbum(1)

        assertNotNull(list)
        assert(list[0] is Album)
        assertEquals(1, list[0].userId)
        assertEquals(1, list[0].id)
        assertEquals("quidem molestiae enim", list[0].title)
        assertEquals(1, list[1].userId)
        assertEquals(2, list[1].id)
        assertEquals("sunt qui excepturi placeat culpa", list[1].title)
    }

    @Test
    fun testFetchAlbums_Negative() = runBlocking {
        coEvery { userRepository.fetchAlbums(1) } throws IllegalStateException("err")
        try {
            val list = getUsers.executeAlbum(1)
        } catch (e: Exception) {
            assert(e is IllegalStateException)
        }
    }

    @Test
    fun testFetchPhotos_Positive() = runBlocking {
        var jsonString =
            "[{\"albumId\":1,\"id\":1,\"title\":\"accusamus beatae ad facilis cum similique qui sunt\",\"url\":\"https://via.placeholder.com/600/92c952\",\"thumbnailUrl\":\"https://via.placeholder.com/150/92c952\"},{\"albumId\":1,\"id\":2,\"title\":\"reprehenderit est deserunt velit ipsam\",\"url\":\"https://via.placeholder.com/600/771796\",\"thumbnailUrl\":\"https://via.placeholder.com/150/771796\"},{\"albumId\":1,\"id\":3,\"title\":\"officia porro iure quia iusto qui ipsa ut modi\",\"url\":\"https://via.placeholder.com/600/24f355\",\"thumbnailUrl\":\"https://via.placeholder.com/150/24f355\"}]";
        val listPhotoType = object : TypeToken<List<Photo>>() {}.type
        val listPhoto = Gson().fromJson<List<Photo>>(jsonString, listPhotoType)

        coEvery { userRepository.fetchPhotos(any()) } returns listPhoto

        val list = getUsers.executePhoto(1)

        assertNotNull(list)
        assert(list[0] is Photo)
        assertEquals(1, list[0].albumId)
        assertEquals(2, list[0].id)
        assertEquals("accusamus beatae ad facilis cum similique qui sunt", list[0].title)
        assertEquals(1, list[1].albumId)
        assertEquals(2, list[1].id)
        assertEquals("https://via.placeholder.com/600/771796", list[1].url)
    }

    @Test
    fun testFetchPhotos_Negative() = runBlocking {
        coEvery { userRepository.fetchPhotos(1) } throws IllegalStateException("err")
        try {
            val list = getUsers.executePhoto(1)
        } catch (e: Exception) {
            assert(e is IllegalStateException)
        }
    }

}