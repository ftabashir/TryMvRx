package ir.tabashir.trymvrx.network.model

import com.squareup.moshi.Json

data class Category(
    @field:Json(name = "title") val title: String
)