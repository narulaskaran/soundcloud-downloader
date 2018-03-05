/*
 * Karan Narula
 * Playing around with developer key
 * You need to hardcode your the desired track ID #
 * Certain tracks can be downloaded based on privacy and sharing settings
 *  set by the uploader/artist
 */
package soundcloudstream;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

public class SoundCloudStream {

    public static void main(String[] args) {

        final byte[] audioData = new byte[500000000];
        String trackId = "YOUR_TRACK_ID";

        Thread retrieveAudioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int bytes = 0;

                try {
                    URL obj = new URL(String.format("https://api.soundcloud.com/tracks/%s/stream?client_id=%s", trackId, "YOUR_CLIENT_ID"));
                    InputStream stream = obj.openStream();

                    File file = new File(trackId + ".mp3");
                    if (!file.exists()) {
                        file.createNewFile();
                        OutputStream out = new FileOutputStream(file);

                        int bytesRead = 0;
                        while ((bytesRead = stream.read(audioData, bytes, 100)) >= 0) {
                            out.write(audioData, bytes, 100);
                            bytes = bytes + bytesRead;
                        }

                        out.close();
                        stream.close();
                    }

//                AudioData audio = new AudioData(audioData);
//                AudioDataStream adstream = new AudioDataStream(audio);
//                AudioPlayer.player.start(adstream);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        });
        retrieveAudioThread.start();

    }

}
