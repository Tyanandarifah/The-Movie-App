package id.indocyber.common.base

import okhttp3.ResponseBody

open class BaseResponse<T>(
    val data: T?, val error: Throwable?, code: Int?, val errorBody: ResponseBody?) {
    companion object {
        fun <T> success(value: T) : BaseResponse<T> = BaseResponseSuccess(value)
        fun <T> failure(exception: Throwable?) : BaseResponse<T> =
            BaseResponseError(exception, BaseResponseError.ERROR_SYSTEM, null)
        fun <T> loading() : BaseResponse<T> = BaseResponseLoading()
        fun <T> failureBackend(body: ResponseBody?) : BaseResponse<T> =
            BaseResponseError(null, BaseResponseError.ERROR_BACKEND, body)
    }
    class BaseResponseSuccess<T>(data: T)
        : BaseResponse<T>(data,null,null,null) { }

    class BaseResponseError<T>(exc: Throwable?, code: Int, responseBody: ResponseBody?
    ) : BaseResponse<T>(
        null, exc, code, responseBody
    ) {
        companion object{
            const val ERROR_SYSTEM = -1
            const val ERROR_BACKEND = -2
        }
    }
    class BaseResponseLoading<T>()
        : BaseResponse<T>(null, null, null, null) {}
}