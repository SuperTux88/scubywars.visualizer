package de.tdng2011.game.visualizer

import de.tdng2011.game.library.connection.{ RelationTypes, AbstractClient }
import de.tdng2011.game.library.event.{ CollisionEvent, PlayerKilledEvent, ShotSpawnedEvent }
import de.tdng2011.game.library.World

class Client(hostname: String, val sound: Boolean = false, val halfSize: Boolean = false) extends {
  val visualizer = new Visualizer(halfSize)
} with AbstractClient(hostname, RelationTypes.VisualizerNG) {

  visualizer.start

  if (sound) {
    new SoundPlayer("/background.wav", true)
  }

  def processWorld(world: World) {
    visualizer !! world
  }

  override def processScoreBoard(scoreBoard: Map[Long, Int]) {
    visualizer.currentScores = scoreBoard.toList.sort { (a, b) => a._2 > b._2 }
  }

  override def processNames(names: Map[Long, String]) {
    visualizer.currentNames = names
  }

  override def shotSpawnedEvent(event: ShotSpawnedEvent) {
    if (sound) {
      val x = scala.util.Random.shuffle(Sounds.shotSounds)
      var first = x(0)
      first.playSound
    }
  }

  override def playerKilledEvent(event: PlayerKilledEvent) {
    if (sound) {
      val x = scala.util.Random.shuffle(Sounds.killSounds)
      var first = x(0)
      first.playSound
    }
  }

  override def playerCollisionEvent(event: CollisionEvent) {
    if (sound) {
      val x = scala.util.Random.shuffle(Sounds.playerCollisionSounds)
      var first = x(0)
      first.playSound
    }
  }

}

object Sounds {
  val shotSounds = 0 to 4 map (i => new SoundPlayer("/shotSpawned" + i + ".wav", false, -5))
  val killSounds = 0 to 5 map (i => new SoundPlayer("/playerKilled" + i + ".wav", false, -5))
  val playerCollisionSounds = 0 to 1 map (i => new SoundPlayer("/playerCollision" + i + ".wav"))
}

