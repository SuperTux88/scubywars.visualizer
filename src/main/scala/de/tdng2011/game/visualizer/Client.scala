package de.tdng2011.game.visualizer

import de.tdng2011.game.library.connection.{RelationTypes, AbstractClient}
import de.tdng2011.game.library.World

class Client(hostname : String) extends AbstractClient(hostname, RelationTypes.VisualizerNG) {

  val shotSounds = 1 to 4 map(i => new SoundPlayer("/shotSpawned"+i+".wav"))
  val killSounds = 1 to 5 map(i => new SoundPlayer("/playerKilled"+i+".wav"))

  
  Visualizer.start
  //new Thread(new SoundPlayer("/background.wav")).start
  
  
  def processWorld(world : World) {
    Visualizer !! world
  }

  override def processScoreBoard(scoreBoard : Map[Long, Int]) {
    Visualizer.currentScores = scoreBoard.toList.sort{ (a, b) => a._2 > b._2 }
  }

  override def processNames(names : Map[Long, String]) {
    Visualizer.currentNames = names
  }
  
  override def shotSpawnedEvent() {
    logger.info("is it possible to play a shot sound?")
    if(shotSounds != null) {
      logger.info("playing shot sound")
    //val x = scala.util.Random.shuffle(shotSounds)
   // var first = x(0)
   // first.run
//    new Thread(first).start
    }
  }
  
  override def playerKilledEvent(){
    
    if(killSounds != null){
      logger.info("playing kill sound");
    //val x = scala.util.Random.shuffle(killSounds)
    //var first = x(0)
    //first.run()
    //new Thread(first).start
    }
  }
}

