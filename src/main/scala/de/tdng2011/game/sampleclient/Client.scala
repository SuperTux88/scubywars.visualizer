package de.tdng2011.game.sampleclient

import java.net.Socket
import de.tdng2011.game.library.{Player, Shot}
import visual.Visualizer
import java.io.DataInputStream
import de.tdng2011.game.library.util.{ByteUtil, StreamUtil}

object Client {
  val playerType  = 0
  val shotType    = 1
  val worldType   = 3

  var entityList = List[Any]()

  Visualizer.start
  val connection : Socket = connect()
  handshakePlayer

  def main(args : Array[String]){
    val stream = new DataInputStream(connection.getInputStream)

    connection.getOutputStream.write(ByteUtil.toByteArray(true, false, true, false))

    while(true){
      val buf = StreamUtil.read(stream, 2)
      val id = buf.getShort

      if(id == playerType) {
        entityList = new Player(stream) :: entityList
      } else if (id == shotType) {
        entityList = new Shot(stream) :: entityList
      } else if(id == worldType) {
        Visualizer !! entityList
        entityList = List[Any]()
      } else {
        println("barbra streisand! (unknown bytes, wth?!) typeId: " + id)
        System.exit(-1)
      }
    }
  }


  def handshakeVisualizer =  connection.getOutputStream.write(ByteUtil.toByteArray(1.shortValue));
  def handshakePlayer     =  {
    connection.getOutputStream.write(ByteUtil.toByteArray(0.shortValue))
    val response = StreamUtil.read(new DataInputStream(connection.getInputStream), 9);
    println("response code: " + response.get)
    println("publicId: " + response.getLong)
  };

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