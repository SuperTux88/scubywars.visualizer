package de.tdng2011.game.visualizer

import java.net.Socket
import java.io.DataInputStream
import de.tdng2011.game.library.util.{ByteUtil, StreamUtil}
import de.tdng2011.game.library.{EntityTypes, Player, Shot}

object Client {
  val playerType  = 0
  val shotType    = 1
  val worldType   = 3


  Visualizer.start
  val connection : Socket = connect()
  handshakeVisualizer 

  def getFrame(stream : DataInputStream) : List[Any] = StreamUtil.read(stream,2).getShort match {
    case `playerType` => new Player(stream) :: getFrame(stream)
    case `shotType`   => new Shot(stream) :: getFrame(stream)
    case `worldType`  => Nil
    case x => {
          println("barbra streisand! (unknown bytes, wth?!) typeId: " + x)
          System.exit(-1)
          Nil // make the compiler happy..
    }
  }


  def main(args : Array[String]){
    val stream = new DataInputStream(connection.getInputStream)
    connection.getOutputStream.write(ByteUtil.toByteArray(true, false, true, false))
    while(true) Visualizer !! getFrame(stream)
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
