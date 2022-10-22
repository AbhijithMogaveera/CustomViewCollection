package com.abhijith.search_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.painterResource

sealed class SearchState{
    object Opened: SearchState() {}
    object Closed: SearchState() {}
}

interface Callback{
    fun onSearchStateChanged(state: SearchState)
    fun onSearchTextChanged(string: String)
}

class SearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    @Composable
    override fun Content() {
        Row {
            IconButton(onClick = { /*TODO*/ }) {
            }
        }
    }

}