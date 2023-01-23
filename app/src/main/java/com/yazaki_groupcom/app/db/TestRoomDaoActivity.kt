package com.yazaki_groupcom.app.db

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yazaki_groupcom.app.ThisApp
import com.yazaki_groupcom.app.databinding.ActivityTestRoomDaoBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class TestRoomDaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestRoomDaoBinding

    private lateinit var posts: List<Post>

    // Obtain ViewModel from ViewModelProviders
    private lateinit var viewModel: TestRoomDaoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestRoomDaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Room Dao 普通使用"

        viewModel = TestRoomDaoViewModel(this)
        binding.viewModel = viewModel

        binding.dataGet.setOnClickListener { dataGet() }
        binding.dataGetSelect.setOnClickListener { dataGetSelect() }
        binding.dataInsert.setOnClickListener { dataInsert() }
        binding.dataInsertArray.setOnClickListener { dataInsertList() }
        binding.dataDeleteAll.setOnClickListener { dataDeleteAll() }
        binding.dataDelete.setOnClickListener { dataDelete() }
        binding.dataCount.setOnClickListener { dataCount() }
        binding.dataFlow.setOnClickListener { dataFlow() }
        binding.flowListenStart.setOnClickListener {
            //开启协程对数据库的表进行监听
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                flowListen()
            }
        }


    }

    //开启协程对数据库的表进行监听
    private suspend fun flowListen() {
        withContext(lifecycleScope.coroutineContext) {
            viewModel.getListFlow().collect {
                binding.flowListenResult.text = it.toString()
            }
        }

    }


    private fun dataCount() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val postDao = ThisApp.database.postDao()

            withContext(Dispatchers.Main) {
                binding.resultsTextview.text = "count:" + postDao.count().toString()
            }
        }
    }


    private fun dataFlow() {
        runBlocking {
            flow {
                val postDao = ThisApp.database.postDao()
                emit(postDao)
            }
                .onStart { Log.e(TAG, "flowViewModel: Starting flow") }
                .onEach {
                    binding.resultsTextview.text = "dataFlow:" + it.getAll().toString()
                }
                .catch { binding.error.text = it.message }
                .onCompletion { if (it == null) Log.e(TAG, "Completed successfully") }
                .collect()
        }
    }


    private fun dataGetSelect() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val postDao = ThisApp.database.postDao()

            posts = postDao.getSelect(1)
            Log.e(TAG, "onCreate: posts:$posts")

            withContext(Dispatchers.Main) {
                binding.resultsTextview.text = posts.toString()
            }
        }
    }


    private fun dataDelete() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val postDao = ThisApp.database.postDao()

            val newPost = Post(1, "1", "1")
            postDao.delete(newPost)

            posts = postDao.getAll()
            Log.e(TAG, "onCreate: posts:$posts")

            withContext(Dispatchers.Main) {
                binding.resultsTextview.text = posts.toString()
            }
        }
    }


    private fun dataDeleteAll() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val postDao = ThisApp.database.postDao()
            postDao.deleteAll()

            posts = postDao.getAll()
            Log.e(TAG, "onCreate: posts:$posts")

            withContext(Dispatchers.Main) {
                binding.resultsTextview.text = posts.toString()
            }
        }
    }


    private fun dataGet() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val postDao = ThisApp.database.postDao()

            posts = postDao.getAll()
            Log.e(TAG, "onCreate: posts:$posts")

            withContext(Dispatchers.Main) {
                binding.resultsTextview.text = posts.toString()
            }
        }
    }


    private fun dataInsert() {

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val postDao = ThisApp.database.postDao()

            var newPost = Post(1, "1", "1")
            postDao.insert(newPost)

            newPost = Post(2, "2", "2")
            postDao.insert(newPost)

            newPost = Post(3, "3", "3")
            postDao.insert(newPost)

            posts = postDao.getAll()
            Log.e(TAG, "onCreate: posts:$posts")

            withContext(Dispatchers.Main) {
                binding.resultsTextview.text = posts.toString()

            }
        }
    }


    private fun dataInsertList() {

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val postDao = ThisApp.database.postDao()

            val postList = arrayListOf<Post>()

            postList.add(Post(4, "4", "4"))
            postList.add(Post(5, "5", "5"))

            postDao.insertAll(postList)

            posts = postDao.getAll()
            Log.e(TAG, "onCreate: posts:$posts")

            withContext(Dispatchers.Main) {
                binding.resultsTextview.text = posts.toString()

            }
        }
    }

    //有意外发生时的对应线程
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "throwable: $throwable")
        binding.error.text = throwable.toString()
    }


    companion object {

        private const val TAG = "TestRoomDaoActivity"
        private const val DATABASE_NAME = "Posts_db"
    }
}