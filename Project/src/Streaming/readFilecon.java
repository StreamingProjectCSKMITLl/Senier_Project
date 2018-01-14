/** *****************************************************************************
 * Copyright (c) 2008, 2010 Xuggle Inc.  All rights reserved.
 *
 * This file is part of Xuggle-Xuggler-Main.
 *
 * Xuggle-Xuggler-Main is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Xuggle-Xuggler-Main is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Xuggle-Xuggler-Main.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************
 */
package Streaming;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;

/**
 * Takes a media container, finds the first video stream, decodes that stream,
 * and then plays the audio and video.
 *
 * This code does a VERY coarse job of matching time-stamps, and thus the audio
 * and video will float in and out of slight sync. Getting time-stamps
 * syncing-up with audio is very system dependent and left as an exercise for
 * the reader.
 *
 * @author aclarke
 *
 */
public class readFilecon {

    public static void main(String[] args)
  {
    String sourceUrl = "media/test.MP4";

    readFilecon videoPlayer = new readFilecon();
    videoPlayer.play(sourceUrl);
  }

  public void play(String sourceUrl)
  {
    IMediaReader reader = ToolFactory.makeReader(sourceUrl);
    reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

    final MyVideoFrame frame = new MyVideoFrame();
    frame.setSize(new Dimension(500, 500));

    MediaListenerAdapter adapter = new MediaListenerAdapter()
    {
      @Override
      public void onVideoPicture(IVideoPictureEvent event)
      {
        frame.setImage((BufferedImage) event.getImage());
      }
    };
    reader.addListener(adapter);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    while (reader.readPacket() == null)
      do {} while(false);
  }

  @SuppressWarnings("serial")
  private class MyVideoFrame extends JFrame
  {
    Image image;

    public void setImage(final Image image)
    {
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          MyVideoFrame.this.image = image;
          repaint();
        }
      });
    }

    @Override
    public synchronized void paint(Graphics g)
    {
      if (image != null)
      {
        g.drawImage(image, 0, 0, null);
      }
    }
  }
}
