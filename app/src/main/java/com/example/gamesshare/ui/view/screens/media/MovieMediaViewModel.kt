package com.example.gamesshare.ui.view.screens.media

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource.Factory
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.smoothstreaming.SsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieMediaViewModel @Inject constructor() : ViewModel() {
    private var _player: ExoPlayer? = null
    val player: ExoPlayer?
        get() = _player

    @OptIn(UnstableApi::class)
    fun initPlayer(context: Context, uriVideo:String) {
        if (_player == null) {
            _player = ExoPlayer.Builder(context).build().apply {
                val defaultHttpDataSource = DefaultHttpDataSource.Factory()
                val uri = Uri.parse(uriVideo)
                val media = buildMediaSource(uri, defaultHttpDataSource, null)
                setMediaSource(media)
                playWhenReady = true
                prepare()
            }
        }
    }

    fun releasePlayer() {
        _player?.release()
        _player = null
    }

    @OptIn(UnstableApi::class)
    private fun buildMediaSource(
        uri: Uri?,
        defaultHttpDataSource: DefaultHttpDataSource.Factory,
        exception: String?
    ): MediaSource {
        val type = Util.inferContentType(uri!!, exception)
        return when (type) {
            C.CONTENT_TYPE_DASH -> Factory(defaultHttpDataSource).createMediaSource(
                MediaItem.fromUri(uri)
            )
            C.CONTENT_TYPE_SS -> SsMediaSource.Factory(defaultHttpDataSource).createMediaSource(
                MediaItem.fromUri(uri)
            )
            C.CONTENT_TYPE_HLS -> HlsMediaSource.Factory(defaultHttpDataSource).createMediaSource(
                MediaItem.fromUri(uri)
            )
            C.CONTENT_TYPE_OTHER -> ProgressiveMediaSource.Factory(defaultHttpDataSource)
                .createMediaSource(MediaItem.fromUri(uri))
            else -> throw IllegalArgumentException("Unsupported type $type")
        }
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }
}