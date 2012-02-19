package de.tdng2011.game.visualizer

import actors.Actor
import com.googlecode.scala.sound.sampled._
import javax.sound.sampled.{LineEvent, AudioSystem}
import de.tdng2011.game.library.util.ScubywarsLogger
import scala.util.control.Breaks._

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 19.02.12
 * Time: 16:01
 */

class SoundPlayer(fileName : String) extends Runnable {
 val file = getClass getResourceAsStream fileName
 val stream = AudioSystem getAudioInputStream file
 val clip = AudioSystem.getClip()
 clip.open(stream)
 //clip.start()

  def run = playSound

  def playSound() {
        clip.stop();
        clip.setFramePosition(0);
        clip.start(); 
  }
}

