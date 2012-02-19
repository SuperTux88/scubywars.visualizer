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

object SoundPlayer extends Actor with ScubywarsLogger {

  var scoreBoard = Map[Long, Int]()

  var playing = false

  def act = {
    loop {
      react {
        case x : CrashMessage => {
//          if (playing) {
//            breakable {
//              for ((id, score) <- scoreBoard) {
//                if (score > x.scoreBoard.get(id).getOrElse(0)) {
//                  new Thread(new SoundPlayer("/explosion-01.wav")).start
//                  logger.debug("crash: " + id)
//                  break
//                }
//              }
//            }
//          }
//          scoreBoard = x.scoreBoard
        }
        
        case 'shotSpawned => {
          new Thread(new SoundPlayer("/shotSpawned.wav")).start
        }
        
        case 'playerKilled => {
        	new Thread(new SoundPlayer("/playerKilled.wav")).start
        }

        case x => {
          logger.warn("unknown message received " + x)
        }
      }
    }
  }
}

class SoundPlayer(fileName : String) extends Runnable{

  def run = playSound

  def playSound() {
    val file = getClass getResourceAsStream fileName
    val stream = AudioSystem getAudioInputStream file

    using(SourceDataLine(stream)) {
      line => {
        line.addLineListener((e: LineEvent) => {println(e)})
        stream >> line
      }
    }
  }
}

case class CrashMessage(scoreBoard : Map[Long, Int])
