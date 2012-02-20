package de.tdng2011.game.visualizer

import actors.Actor
import javax.sound.sampled.{ LineEvent, AudioSystem }

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 19.02.12
 * Time: 16:01
 */

class SoundPlayer(fileName: String, repeat: Boolean = false) {
  val now = System.currentTimeMillis()
  val file = getClass getResource fileName
  val stream = AudioSystem getAudioInputStream file
  val clip = AudioSystem.getClip()
  clip.open(stream)

  if (repeat) clip.loop(0)

  def playSound() {
    clip.stop();
    clip.setFramePosition(0);
    clip.start();
  }
}

