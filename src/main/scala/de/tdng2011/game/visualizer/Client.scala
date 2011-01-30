package de.tdng2011.game.visualizer

import java.net.Socket
import java.io.DataInputStream
import de.tdng2011.game.library.util.{ByteUtil, StreamUtil}
import de.tdng2011.game.library.{EntityTypes, Player, Shot}

object Client {
  var entityList = List[Any]()

  Visualizer.start
  val connection : Socket = connect()
  handshakeVisualizer

  def main(args : Array[String]){
    val iStream = new DataInputStream(connection.getInputStream)

    while(true){
      val buf = StreamUtil.read(iStream, 2)
      val id = buf.getShort

      id match {
        case x if x == EntityTypes.Player.id => entityList = entityList :+ new Player(iStream)
        case x if x == EntityTypes.Shot.id   => entityList = entityList :+ new Shot(iStream)
        case x if x == EntityTypes.World.id  => {
          Visualizer !! entityList
          entityList = List[Any]()
        }
        case x => {
          println("barbra streisand! (unknown bytes, wth?!) typeId: " + id)
          System exit -1
        }
      }
    }
  }

  def handshakeVisualizer = connection.getOutputStream.write(ByteUtil.toByteArray(1.shortValue))

  def connect() : Socket = {
    try {
      new Socket("localhost",1337)
    } catch {
      case e => {
        println("connecting failed. retrying in 5 seconds");
        Thread.sleep(5000)
        connect()
      }
    }
  }
}