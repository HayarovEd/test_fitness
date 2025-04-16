package com.tom.testfitness.ui.dashboard

import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.tom.testfitness.domain.repository.RemoteRepository
import com.tom.testfitness.domain.utils.BUNGLE_DESCRIPTION
import com.tom.testfitness.domain.utils.BUNGLE_ID
import com.tom.testfitness.domain.utils.Resource
import com.tom.testfitness.domain.utils.UNKNOWN_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: RemoteRepository,
    val player: ExoPlayer,
) : ViewModel() {

    private val id = savedStateHandle.get<Int>(BUNGLE_ID)
    private val description = savedStateHandle.get<String>(BUNGLE_DESCRIPTION)

    private val _state = MutableLiveData<PlayerFragmentState>(PlayerFragmentState.Loading)
    val state = _state

    init {
        loadRemoteData()
    }

    private fun loadRemoteData() {
        id?.let {
            viewModelScope.launch {
                when (val result = repository.getVideoById(id)) {
                    is Resource.Error -> {
                        _state.value = PlayerFragmentState.Error(result.message ?: UNKNOWN_ERROR)
                    }

                    is Resource.Success -> {
                        _state.value = result.data?.let { video ->
                            PlayerFragmentState.Success(
                                description = description ?: ""
                            )
                        }
                        result.data?.let { video ->
                            playLiveStream(video.link)
                        }
                    }
                }
            }
        }
    }

    private fun playLiveStream(link: String) {
        viewModelScope.launch {
            val uri = link.toUri()
            val mediaItem =
                MediaItem.Builder()
                    .setUri(uri)
                    .setLiveConfiguration(
                        MediaItem.LiveConfiguration.Builder().setMaxPlaybackSpeed(1.02f).build()
                    )
                    .build()
            player.apply {
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
            }
        }
    }
}