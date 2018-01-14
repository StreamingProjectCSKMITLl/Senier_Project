/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Streaming;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.manager.MediaManager;
import Streaming.VlcjTest;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

/**
 *
 * @author NattapatN
 */
public class testPlaylist {

    public static void main(String[] args) throws Exception {
        boolean found = new NativeDiscovery().discover();
        System.out.println(found);
        System.out.println(LibVlc.INSTANCE.libvlc_get_version());

        String media = "screen://";
        String options = formatRtpStream("230.0.0.1", 5555);

        System.out.println("Streaming '" + media + "' to '" + options + "'");

        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(args);
        EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();

        Canvas canvas = new Canvas();
        canvas.setBackground(Color.black);
        CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
        mediaPlayer.setVideoSurface(videoSurface);

        // Creating a JFrame to display stream (duplicate)
        JFrame f = new JFrame("vlcj duplicate output test");
        f.add(canvas);
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        mediaPlayer.playMedia(media,
                options,
                ":no-sout-rtp-sap",
                ":no-sout-standard-sap",
                ":sout-all",
                ":sout-keep"
        );

        // Don't exit
        Thread.currentThread().join();
    }

    private static String formatRtpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=");
        // Transcode with codec (mp4v here), 30 FPS, not resized (scale 1), audio disabled (only video).
        sb.append("#transcode{vcodec=mp4v,fps=30,scale=1,noaudio}");
        // Creating RTP address
        sb.append(":rtp{dst=");
        sb.append(serverAddress);
        sb.append(",port=");
        sb.append(serverPort);
        // Encapsulation method used for the resulting stream, this option has to be set.
        sb.append(",mux=ts}");
        return sb.toString();
    }

}
