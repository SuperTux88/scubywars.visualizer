package de.tdng2011.game.visualizer

import java.net.Socket
import java.io.DataInputStream
import de.tdng2011.game.library.util.{ByteUtil, StreamUtil}
import de.tdng2011.game.library.{EntityTypes, Player, Shot}

object Client {
  val playerType = EntityTypes.Player.id.shortValue
  val shotType = EntityTypes.Shot.id.shortValue
  val worldType = EntityTypes.World.id.shortValue

  Visualizer.start
  val connection : Socket = connect()
  handshakeVisualizer 

  def getFrame(iStream : DataInputStream) : List[Any] = StreamUtil.read(iStream, 2).getShort match {
    case `playerType` => new Player(iStream) :: getFrame(iStream)
    case `shotType`   => new Shot(iStream) :: getFrame(iStream)
    case `worldType`  => {
      val size = StreamUtil.read(iStream, 4).getInt
      StreamUtil.read(iStream, size)
      Nil
    }
    case x => {
          println("barbra streisand! (unknown bytes, wth?!) typeId: " + x)
          System.exit(-1)
          Nil // make the compiler happy..
    }
  }


  def main(args : Array[String]){
    val iStream = new DataInputStream(connection.getInputStream)
    while(true) Visualizer !! getFrame(iStream)
  }

  def handshakeVisualizer = connection.getOutputStream.write(ByteUtil.toByteArray(EntityTypes.Handshake, 1.shortValue))

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
