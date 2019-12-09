package ir.tabashir.trymvrx.utils

data class PagedList<T>(
    val data: List<T>,
    val pageSize: Int,
    val hasMore: Boolean
) : List<T> by data {

    fun nextPage(): Int {
        if (data.size < pageSize)
            return 0
        else
            return data.size / pageSize + 1
    }

    fun withNextData(nextData: List<T>) = PagedList(
        data = data + nextData,
        pageSize = pageSize,
        hasMore = nextData.size == pageSize
    )

    companion object {

        fun <T> empty(pageSize: Int = 20) = PagedList<T>(
            data = emptyList(),
            pageSize = pageSize,
            hasMore = true
        )

    }

}