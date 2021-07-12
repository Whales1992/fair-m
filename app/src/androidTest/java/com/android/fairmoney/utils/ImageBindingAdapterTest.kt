package com.android.fairmoney.utils

import org.junit.Test
import org.junit.Assert.*

class ImageBindingAdapterTest {
    private var imageBindingAdapter = ImageBindingAdapter(null, "testing...", null)

    @Test
    fun urlNotNullOrEmpty() {
        assertEquals(imageBindingAdapter.urlNotNullOrEmpty(), true)
    }
}