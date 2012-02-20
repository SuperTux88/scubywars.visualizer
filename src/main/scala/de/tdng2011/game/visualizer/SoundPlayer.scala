package de.tdng2011.game.visualizer

import actors.Actor
import javax.sound.sampled.{ LineEvent, AudioSystem }
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 19.02.12
 * Time: 16:01
 */

class SoundPlayer(fileName: String, repeat: Boolean = false, gain : Float = 0) {
  val now = System.currentTimeMillis()
  val file = getClass getResource fileName
  val stream = AudioSystem getAudioInputStream file
  val clip = AudioSystem.getClip()
  clip.open(stream)
 
  val volume = clip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl]
  volume.setValue(gain)
  
  if (repeat) {
    clip.setLoopPoints(0, -1)
    clip.loop(Clip.LOOP_CONTINUOUSLY)
    clip.start()
  }

  def playSound() {
    clip.stop();
    clip.setFramePosition(0);
    clip.start();
  }
}

