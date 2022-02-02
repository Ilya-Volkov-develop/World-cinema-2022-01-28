package ru.iliavolkov.worldcinema.room

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.room.Room
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.iliavolkov.worldcinema.repositiry.ApiRequest
import ru.iliavolkov.worldcinema.utils.BASE_URL
import ru.iliavolkov.worldcinema.utils.DB_NAME
import java.util.*

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object{
        private var appInstance: App? = null
        private var db: HistoryDatabase? = null
        lateinit var player: SimpleExoPlayer
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                ))
            .build().create(ApiRequest::class.java)!!

        fun getHistoryFilmDao(): HistoryFilmDAO {
            if (db ==null){
                if (appInstance ==null){
                    throw IllformedLocaleException("Всё плохо")
                } else{
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        HistoryDatabase::class.java, DB_NAME)
                        .build()
                }
            }
            return db!!.historyWeatherDao()
        }

        fun initPlayer(playerView: PlayerView,context: Context){
            val adaptiveTrackSelection = AdaptiveTrackSelection.Factory(DefaultBandwidthMeter())
            player = ExoPlayerFactory.newSimpleInstance(context,
                DefaultRenderersFactory(context),
                DefaultTrackSelector(adaptiveTrackSelection),
                DefaultLoadControl()
            )
            playerView.player = player
        }
        fun loadVod(url : String,context: Context){
            val dataSourceFactory = DefaultDataSourceFactory(context,
                Util.getUserAgent(context,"Exo"), DefaultBandwidthMeter()
            )
            val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url))
            player.prepare(mediaSource)
            player.playWhenReady = true
        }
    }
}