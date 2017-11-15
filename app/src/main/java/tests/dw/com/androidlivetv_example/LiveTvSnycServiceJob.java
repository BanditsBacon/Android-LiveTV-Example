package tests.dw.com.androidlivetv_example;

import android.media.tv.TvContract;
import android.net.Uri;

import com.google.android.media.tv.companionlibrary.EpgSyncJobService;
import com.google.android.media.tv.companionlibrary.model.Channel;
import com.google.android.media.tv.companionlibrary.model.InternalProviderData;
import com.google.android.media.tv.companionlibrary.model.Program;

import java.util.ArrayList;
import java.util.List;

public class LiveTvSnycServiceJob extends EpgSyncJobService {

    private String MPEG_DASH_CHANNEL_NAME = "DW Deutsch Live";
    private String MPEG_DASH_CHANNEL_NUMBER = "3";
    private String MPEG_DASH_CHANNEL_LOGO
            = "http://www.dw.com/image/17906021_306.jpg";
    private int MPEG_DASH_ORIGINAL_NETWORK_ID = 101;

    private String TEARS_OF_STEEL_TITLE = "Fokus Europa";
    private String TEARS_OF_STEEL_DESCRIPTION = "News from Europa";
    private String TEARS_OF_STEEL_ART
            = "https://tvdownloaddw-a.akamaihd.net/stills/images/je/je20171010_catal08b_image_1024x576_3.jpg";

    private String TEARS_OF_STEEL_SOURCE
            = "http://dwstream4-lh.akamaihd.net/i/dwstream4_live@131329/master.m3u8";
    private static final long TEARS_OF_STEEL_START_TIME_MS = 0;
    private static final long TEARS_OF_STEEL_DURATION_MS = 734 * 1000;

    @Override
    public List<Channel> getChannels() {
        List<Channel> channelList = new ArrayList<>();

        //Add a channel programmatically
        InternalProviderData internalProviderData = new InternalProviderData();
        internalProviderData.setRepeatable(true);
        Channel channelTears = new Channel.Builder()
                .setDisplayName(MPEG_DASH_CHANNEL_NAME)
                .setDisplayNumber(MPEG_DASH_CHANNEL_NUMBER)
                .setChannelLogo(MPEG_DASH_CHANNEL_LOGO)
                .setOriginalNetworkId(MPEG_DASH_ORIGINAL_NETWORK_ID)
                .setInternalProviderData(internalProviderData)
                .build();
        channelList.add(channelTears);
        return channelList;
    }

    @Override
    public List<Program> getProgramsForChannel(Uri channelUri, Channel channel, long startMs, long endMs) {
        // Programatically add channel
        List<Program> programsTears = new ArrayList<>();
        InternalProviderData internalProviderData = new InternalProviderData();

        internalProviderData.setVideoUrl(TEARS_OF_STEEL_SOURCE);
        programsTears.add(new Program.Builder()
                .setTitle(TEARS_OF_STEEL_TITLE)
                .setStartTimeUtcMillis(TEARS_OF_STEEL_START_TIME_MS)
                .setEndTimeUtcMillis(TEARS_OF_STEEL_START_TIME_MS + TEARS_OF_STEEL_DURATION_MS)
                .setDescription(TEARS_OF_STEEL_DESCRIPTION)
                .setCanonicalGenres(new String[] {TvContract.Programs.Genres.TECH_SCIENCE,
                        TvContract.Programs.Genres.MOVIES})
                .setPosterArtUri(TEARS_OF_STEEL_ART)
                .setThumbnailUri(TEARS_OF_STEEL_ART)
                .setInternalProviderData(internalProviderData)
                .build());
        return programsTears;
    }
}
