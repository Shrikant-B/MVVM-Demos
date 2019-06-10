package com.shrikantbadwaik.newsheadlines.view.topheadline

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.shrikantbadwaik.newsheadlines.BR
import com.shrikantbadwaik.newsheadlines.R
import com.shrikantbadwaik.newsheadlines.databinding.ActivityTopHeadlineBinding
import com.shrikantbadwaik.newsheadlines.domain.model.Article
import com.shrikantbadwaik.newsheadlines.domain.util.Constants
import com.shrikantbadwaik.newsheadlines.domain.util.DialogUtil
import com.shrikantbadwaik.newsheadlines.view.articledetails.ArticleDetailsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class TopHeadlineActivity : AppCompatActivity() {
    private val viewModel: TopHeadlineViewModel by viewModel()
    private lateinit var adapter: TopHeadlineRecyclerAdapter
    private lateinit var activityBinding: ActivityTopHeadlineBinding
    private lateinit var clearButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindingAndViewModel()
        setupSearchView()
        setupRecyclerView()
        keyboardStateObserver()
        dialogStateObserver()
        clickEventObserver()
        apiResultObserver()
        apiErrorObserver()
        newsArticleObserver()
        viewModel.getTopHeadlines(this, false, null)
    }

    private fun setupBindingAndViewModel() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_top_headline)
        activityBinding.setVariable(BR.viewModel, viewModel)
        activityBinding.executePendingBindings()
    }

    private fun setupSearchView() {
        clearButton = activityBinding.searchView.findViewById(R.id.search_close_btn)
        activityBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.clear()
                clearButton.visibility = View.GONE
                viewModel.getTopHeadlines(this@TopHeadlineActivity, true, query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = TopHeadlineRecyclerAdapter()
        adapter.setCallback(object : TopHeadlineRecyclerAdapter.AdapterCallback {
            override fun onArticleClicked(article: Article) {
                val intent = Intent(this@TopHeadlineActivity, ArticleDetailsActivity::class.java)
                intent.putExtra(Constants.ARG_ARTICLE, article)
                startActivity(intent)
            }
        })
        activityBinding.recyclerView.adapter = adapter
        activityBinding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        activityBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == 0) hideSoftKeyboard()
            }
        })
    }

    private fun keyboardStateObserver() {
        viewModel.getKeyboardStateLiveData().observe(this, Observer {
            when (it) {
                Constants.KeyboardState.HIDE.name -> hideSoftKeyboard()
                Constants.KeyboardState.SHOW.name -> showSoftKeyboard()
            }
        })
    }

    private fun dialogStateObserver() {
        viewModel.getDialogStateLiveData().observe(this, Observer {
            when (it) {
                Constants.DialogState.QUERY_EMPTY_ERROR_DIALOG.name -> {
                    DialogUtil.showErrorDialog(this, getString(R.string.txt_text_empty), null)
                }
                Constants.DialogState.DEVICE_OFFLINE_DIALOG.name -> {
                    DialogUtil.showErrorDialog(this, getString(R.string.txt_no_internet_connection), null)
                }
            }
        })
    }

    private fun clickEventObserver() {
        viewModel.getClickEventLiveData().observe(this, Observer {
            when (it) {
                Constants.ClickEventState.SELECT_COUNTRY_DIALOG.name -> {
                    DialogUtil.showCountryPickerDialog(this, object : DialogUtil.DialogListener {
                        override fun onButtonClicked() {
                        }

                        override fun onItemSelected(which: String, name: String) {
                            adapter.clear()
                            activityBinding.btnSelectCountry.text = name
                            viewModel.selectCountry(
                                application, resources.getStringArray(R.array.category),
                                which, activityBinding.btnSelectCategory.text.toString()
                            )
                        }
                    })
                }

                Constants.ClickEventState.SELECT_CATEGORY_DIALOG.name -> {
                    DialogUtil.showCategoryPickerDialog(this, object : DialogUtil.DialogListener {
                        override fun onButtonClicked() {
                        }

                        override fun onItemSelected(which: String, name: String) {
                            adapter.clear()
                            activityBinding.btnSelectCategory.text = name
                            viewModel.selectCategory(
                                application, resources.getStringArray(R.array.country),
                                activityBinding.btnSelectCountry.text.toString(), which
                            )
                        }
                    })
                }
            }
        })
    }

    private fun apiResultObserver() {
        viewModel.getApiResultLiveData().observe(this, Observer {
            when (it) {
                Constants.ApiResult.ON_SUCCESS.name -> {
                    activityBinding.recyclerView.visibility = View.VISIBLE
                    activityBinding.imageView01.visibility = View.GONE
                    activityBinding.textView01.visibility = View.GONE
                    clearButton.visibility = View.VISIBLE
                }
                Constants.ApiResult.ON_FAILURE.name -> {
                    activityBinding.recyclerView.visibility = View.GONE
                    activityBinding.imageView01.visibility = View.VISIBLE
                    activityBinding.textView01.visibility = View.VISIBLE
                    clearButton.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun apiErrorObserver() {
        viewModel.getApiErrorLiveData().observe(this, Observer {
            DialogUtil.showErrorDialog(this, it, null)
        })
    }

    private fun newsArticleObserver() {
        viewModel.getNewsArticleLiveData()
            .observe(this, Observer<ArrayList<Article>> { viewModel.setNewsArticleObservable(it) })
    }

    private fun hideSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = this.currentFocus
        view?.let { imm.hideSoftInputFromWindow(view.windowToken, 0) }
    }

    private fun showSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = this.currentFocus
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}