package com.mobile.eflores.ulassessment.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobile.eflores.livedataapi.util.LiveDataResult
import com.mobile.eflores.ulassessment.MainViewModel
import com.mobile.eflores.ulassessment.data.*
import com.mobile.eflores.ulassessment.domain.GetUsers
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var getUsers: GetUsers

    lateinit var mainViewModel: MainViewModel

    val dispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(dispatcher, dispatcher, getUsers)
    }

    @Test
    fun testFetchUsers_Positive() {
        var jsonString = "[{\"id\":1,\"name\":\"Leanne Graham\",\"username\":\"Bret\",\"email\":\"Sincere@april.biz\",\"address\":{\"street\":\"Kulas Light\",\"suite\":\"Apt. 556\",\"city\":\"Gwenborough\",\"zipcode\":\"92998-3874\",\"geo\":{\"lat\":\"-37.3159\",\"lng\":\"81.1496\"}},\"phone\":\"1-770-736-8031 x56442\",\"website\":\"hildegard.org\",\"company\":{\"name\":\"Romaguera-Crona\",\"catchPhrase\":\"Multi-layered client-server neural-net\",\"bs\":\"harness real-time e-markets\"}},{\"id\":2,\"name\":\"Ervin Howell\",\"username\":\"Antonette\",\"email\":\"Shanna@melissa.tv\",\"address\":{\"street\":\"Victor Plains\",\"suite\":\"Suite 879\",\"city\":\"Wisokyburgh\",\"zipcode\":\"90566-7771\",\"geo\":{\"lat\":\"-43.9509\",\"lng\":\"-34.4618\"}},\"phone\":\"010-692-6593 x09125\",\"website\":\"anastasia.net\",\"company\":{\"name\":\"Deckow-Crist\",\"catchPhrase\":\"Proactive didactic contingency\",\"bs\":\"synergize scalable supply-chains\"}},{\"id\":3,\"name\":\"Clementine Bauch\",\"username\":\"Samantha\",\"email\":\"Nathan@yesenia.net\",\"address\":{\"street\":\"Douglas Extension\",\"suite\":\"Suite 847\",\"city\":\"McKenziehaven\",\"zipcode\":\"59590-4157\",\"geo\":{\"lat\":\"-68.6102\",\"lng\":\"-47.0653\"}},\"phone\":\"1-463-123-4447\",\"website\":\"ramiro.info\",\"company\":{\"name\":\"Romaguera-Jacobson\",\"catchPhrase\":\"Face to face bifurcated interface\",\"bs\":\"e-enable strategic applications\"}},{\"id\":4,\"name\":\"Patricia Lebsack\",\"username\":\"Karianne\",\"email\":\"Julianne.OConner@kory.org\",\"address\":{\"street\":\"Hoeger Mall\",\"suite\":\"Apt. 692\",\"city\":\"South Elvis\",\"zipcode\":\"53919-4257\",\"geo\":{\"lat\":\"29.4572\",\"lng\":\"-164.2990\"}},\"phone\":\"493-170-9623 x156\",\"website\":\"kale.biz\",\"company\":{\"name\":\"Robel-Corkery\",\"catchPhrase\":\"Multi-tiered zero tolerance productivity\",\"bs\":\"transition cutting-edge web services\"}}]";
        val listUserType = object : TypeToken<List<User>>() {}.type
        val listUser = Gson().fromJson<List<User>>(jsonString, listUserType)

        coEvery { getUsers.executeUser() } returns listUser

        mainViewModel.repositoriesLiveData.observeForever {}

        mainViewModel.fetchUsers()

        assert(mainViewModel.repositoriesLiveData.value != null)
        assert(mainViewModel.repositoriesLiveData.value!!.status == LiveDataResult.STATUS.SUCCESS)
    }

    @Test
    fun testFetchUsers_Negative() {
        coEvery { getUsers.executeUser() } coAnswers { throw Exception("No network") }

        mainViewModel.repositoriesLiveData.observeForever {}

        mainViewModel.fetchUsers()

        assert(mainViewModel.repositoriesLiveData.value != null)
        assert(mainViewModel.repositoriesLiveData.value!!.status == LiveDataResult.STATUS.ERROR)
    }

    @Test
    fun testFetchPosts_Positive() {
        var jsonString =
            "[{\"userId\":1,\"id\":1,\"title\":\"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\"body\":\"quia et suscipit\r\nsuscipit recusandae consequuntur expedita et cum\r\nreprehenderit molestiae ut ut quas totam\r\nnostrum rerum est autem sunt rem eveniet architecto\"},{\"userId\":1,\"id\":2,\"title\":\"qui est esse\",\"body\":\"est rerum tempore vitae\r\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\r\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\r\nqui aperiam non debitis possimus qui neque nisi nulla\"},{\"userId\":1,\"id\":3,\"title\":\"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\"body\":\"et iusto sed quo iure\r\nvoluptatem occaecati omnis eligendi aut ad\r\nvoluptatem doloribus vel accusantium quis pariatur\r\nmolestiae porro eius odio et labore et velit aut\"},{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\r\nsit amet autem assumenda provident rerum culpa\r\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\r\nquis sunt voluptatem rerum illo velit\"},{\"userId\":1,\"id\":5,\"title\":\"nesciunt quas odio\",\"body\":\"repudiandae veniam quaerat sunt sed\r\nalias aut fugiat sit autem sed est\r\nvoluptatem omnis possimus esse voluptatibus quis\r\nest aut tenetur dolor neque\"},{\"userId\":1,\"id\":6,\"title\":\"dolorem eum magni eos aperiam quia\",\"body\":\"ut aspernatur corporis harum nihil quis provident sequi\r\nmollitia nobis aliquid molestiae\r\nperspiciatis et ea nemo ab reprehenderit accusantium quas\r\nvoluptate dolores velit et doloremque molestiae\"},{\"userId\":1,\"id\":7,\"title\":\"magnam facilis autem\",\"body\":\"dolore placeat quibusdam ea quo vitae\r\nmagni quis enim qui quis quo nemo aut saepe\r\nquidem repellat excepturi ut quia\r\nsunt ut sequi eos ea sed quas\"},{\"userId\":1,\"id\":8,\"title\":\"dolorem dolore est ipsam\",\"body\":\"dignissimos aperiam dolorem qui eum\r\nfacilis quibusdam animi sint suscipit qui sint possimus cum\r\nquaerat magni maiores excepturi\r\nipsam ut commodi dolor voluptatum modi aut vitae\"},{\"userId\":1,\"id\":9,\"title\":\"nesciunt iure omnis dolorem tempora et accusantium\",\"body\":\"consectetur animi nesciunt iure dolore\r\nenim quia ad\r\nveniam autem ut quam aut nobis\r\net est aut quod aut provident voluptas autem voluptas\"},{\"userId\":1,\"id\":10,\"title\":\"optio molestias id quia eum\",\"body\":\"quo et expedita modi cum officia vel magni\r\ndoloribus qui repudiandae\r\nvero nisi sit\r\nquos veniam quod sed accusamus veritatis error\"}]";
        val listPostType = object : TypeToken<List<Post>>() {}.type
        val listPost = Gson().fromJson<List<Post>>(jsonString, listPostType)

        coEvery { getUsers.executePost(any()) } returns listPost

        mainViewModel.repositoriesLiveDataPosts.observeForever {}

        mainViewModel.fetchPosts(1)

        assert(mainViewModel.repositoriesLiveDataPosts.value != null)
        assert(mainViewModel.repositoriesLiveDataPosts.value!!.status == LiveDataResult.STATUS.SUCCESS)
    }

    @Test
    fun testFetchPosts_Negative() {
        coEvery { getUsers.executePost(any()) } coAnswers { throw Exception("No network") }

        mainViewModel.repositoriesLiveDataPosts.observeForever {}

        mainViewModel.fetchPosts(1)

        assert(mainViewModel.repositoriesLiveDataPosts.value != null)
        assert(mainViewModel.repositoriesLiveDataPosts.value!!.status == LiveDataResult.STATUS.ERROR)
    }

    @Test
    fun testFetchComments_Positive() {
        var jsonString =
            "[{\"postId\":1,\"id\":1,\"name\":\"id labore ex et quam laborum\",\"email\":\"Eliseo@gardner.biz\",\"body\":\"laudantium enim quasi est quidem magnam voluptate ipsam eos\r\ntempora quo necessitatibus\r\ndolor quam autem quasi\r\nreiciendis et nam sapiente accusantium\"},{\"postId\":1,\"id\":2,\"name\":\"quo vero reiciendis velit similique earum\",\"email\":\"Jayne_Kuhic@sydney.com\",\"body\":\"est natus enim nihil est dolore omnis voluptatem numquam\r\net omnis occaecati quod ullam at\r\nvoluptatem error expedita pariatur\r\nnihil sint nostrum voluptatem reiciendis et\"},{\"postId\":1,\"id\":3,\"name\":\"odio adipisci rerum aut animi\",\"email\":\"Nikita@garfield.biz\",\"body\":\"quia molestiae reprehenderit quasi aspernatur\r\naut expedita occaecati aliquam eveniet laudantium\r\nomnis quibusdam delectus saepe quia accusamus maiores nam est\r\ncum et ducimus et vero voluptates excepturi deleniti ratione\"},{\"postId\":1,\"id\":4,\"name\":\"alias odio sit\",\"email\":\"Lew@alysha.tv\",\"body\":\"non et atque\r\noccaecati deserunt quas accusantium unde odit nobis qui voluptatem\r\nquia voluptas consequuntur itaque dolor\r\net qui rerum deleniti ut occaecati\"},{\"postId\":1,\"id\":5,\"name\":\"vero eaque aliquid doloribus et culpa\",\"email\":\"Hayden@althea.biz\",\"body\":\"harum non quasi et ratione\r\ntempore iure ex voluptates in ratione\r\nharum architecto fugit inventore cupiditate\r\nvoluptates magni quo et\"}]";
        val listCommentType = object : TypeToken<List<Comment>>() {}.type
        val listComment = Gson().fromJson<List<Comment>>(jsonString, listCommentType)

        coEvery { getUsers.executeComment(any()) } returns listComment

        mainViewModel.repositoriesLiveDataComments.observeForever {}

        mainViewModel.fetchComments(1)

        assert(mainViewModel.repositoriesLiveDataComments.value != null)
        assert(mainViewModel.repositoriesLiveDataComments.value!!.status == LiveDataResult.STATUS.SUCCESS)
    }

    @Test
    fun testFetchComments_Negative() {
        coEvery { getUsers.executeComment(any()) } coAnswers { throw Exception("No network") }

        mainViewModel.repositoriesLiveDataComments.observeForever {}

        mainViewModel.fetchComments(1)

        assert(mainViewModel.repositoriesLiveDataComments.value != null)
        assert(mainViewModel.repositoriesLiveDataComments.value!!.status == LiveDataResult.STATUS.ERROR)
    }

    @Test
    fun testFetchAlbums_Positive() {
        var jsonString =
            "[{\"userId\":1,\"id\":1,\"title\":\"quidem molestiae enim\"},{\"userId\":1,\"id\":2,\"title\":\"sunt qui excepturi placeat culpa\"},{\"userId\":1,\"id\":3,\"title\":\"omnis laborum odio\"}]";
        val listAlbumType = object : TypeToken<List<Album>>() {}.type
        val listAlbum = Gson().fromJson<List<Album>>(jsonString, listAlbumType)

        coEvery { getUsers.executeAlbum(any()) } returns listAlbum

        mainViewModel.repositoriesLiveDataAlbums.observeForever {}

        mainViewModel.fetchAlbums(1)

        assert(mainViewModel.repositoriesLiveDataAlbums.value != null)
        assert(mainViewModel.repositoriesLiveDataAlbums.value!!.status == LiveDataResult.STATUS.SUCCESS)
    }

    @Test
    fun testFetchAlbums_Negative() {
        coEvery { getUsers.executeAlbum(any()) } coAnswers { throw Exception("No network") }

        mainViewModel.repositoriesLiveDataAlbums.observeForever {}

        mainViewModel.fetchAlbums(1)

        assert(mainViewModel.repositoriesLiveDataAlbums.value != null)
        assert(mainViewModel.repositoriesLiveDataAlbums.value!!.status == LiveDataResult.STATUS.ERROR)
    }

    @Test
    fun testFetchPhotos_Positive() {
        var jsonString =
            "[{\"albumId\":1,\"id\":1,\"title\":\"accusamus beatae ad facilis cum similique qui sunt\",\"url\":\"https://via.placeholder.com/600/92c952\",\"thumbnailUrl\":\"https://via.placeholder.com/150/92c952\"},{\"albumId\":1,\"id\":2,\"title\":\"reprehenderit est deserunt velit ipsam\",\"url\":\"https://via.placeholder.com/600/771796\",\"thumbnailUrl\":\"https://via.placeholder.com/150/771796\"},{\"albumId\":1,\"id\":3,\"title\":\"officia porro iure quia iusto qui ipsa ut modi\",\"url\":\"https://via.placeholder.com/600/24f355\",\"thumbnailUrl\":\"https://via.placeholder.com/150/24f355\"}]";
        val listPhotoType = object : TypeToken<List<Photo>>() {}.type
        val listPhoto = Gson().fromJson<List<Photo>>(jsonString, listPhotoType)

        coEvery { getUsers.executePhoto(any()) } returns listPhoto

        mainViewModel.repositoriesLiveDataPhotos.observeForever {}

        mainViewModel.fetchPhotos(1)

        assert(mainViewModel.repositoriesLiveDataPhotos.value != null)
        assert(mainViewModel.repositoriesLiveDataPhotos.value!!.status == LiveDataResult.STATUS.SUCCESS)
    }

    @Test
    fun testFetchPhotos_Negative() {
        coEvery { getUsers.executePhoto(any()) } coAnswers { throw Exception("No network") }

        mainViewModel.repositoriesLiveDataPhotos.observeForever {}

        mainViewModel.fetchPhotos(1)

        assert(mainViewModel.repositoriesLiveDataPhotos.value != null)
        assert(mainViewModel.repositoriesLiveDataPhotos.value!!.status == LiveDataResult.STATUS.ERROR)
    }

}