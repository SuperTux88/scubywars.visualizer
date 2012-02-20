package de.tdng2011.game.visualizer

import actors.Actor
import java.util.Random
import swing._
import java.awt.{ Color, Graphics2D, Font }
import math._
import de.tdng2011.game.library.{ ScoreBoard, World, Shot, Player }
import de.tdng2011.game.library.util.{ ScubywarsLogger, Vec2 }

class Visualizer(halfSize: Boolean = false) extends MainFrame with Actor with ScubywarsLogger {

  val factor = if (halfSize) 2 else 1

  val worldSize = 1000 / factor //m

  val lineLength = 30 / factor // todo monster / player radius * 2

  var currentWorld: World = _
  var currentScores = List[(Long, Int)]()
  var currentNames = Map[Long, String]()

  val bgStars = for (x <- 1 to 500 / factor) yield (new Random().nextInt(worldSize), new Random().nextInt(worldSize))
  val fgStars = for (x <- 1 to 100 / factor) yield (new Random().nextInt(worldSize), new Random().nextInt(worldSize))

  title = "Scubywars"
  var mainPanel = new BoxPanel(scala.swing.Orientation.Horizontal) {
    focusable = true
    background = Color.BLACK
    preferredSize = new Dimension(worldSize + 400, worldSize)
  }

  var gameFieldPanel = new Panel {
    focusable = true
    background = Color.BLACK
    preferredSize = new Dimension(mainPanel.size.width - 400 + worldSize, worldSize)

    override def paintComponent(g: Graphics2D) {
      super.paintComponent(g)
      g.setColor(new Color(104, 104, 104))
      for ((x, y) <- bgStars) g.fillRect(x, y, 1, 1)
      g.setColor(new Color(200, 200, 200))
      for ((x, y) <- fgStars) g.fillRect(x, y, 2, 2)

      if (currentWorld != null) {
        currentWorld.players.foreach(x => drawPlayer(g, x))
        currentWorld.shots.foreach(x => drawShot(g, x))
      }
    }
  }

  var statsPanel = new Panel() {
    focusable = true
    background = Color.BLACK

    preferredSize = new Dimension(mainPanel.size.width - gameFieldPanel.size.width, mainPanel.size.height)
    override def paintComponent(g: Graphics2D) {
      super.paintComponent(g)

      var i: Int = 1

      val oldFont = g.getFont

      for ((playerId, score) <- currentScores) {
        g.setFont(new Font("Arial", Font.PLAIN, 20))

        g.setColor(Color.GREEN)
        g.drawString(currentNames.get(playerId).getOrElse(playerId + "") + " " + score, 20, i * 25)
        g.setFont(font)

        i += 1
      }
      g.setColor(Color.GREEN)
      g.drawLine(0, 0, 0, size.height)
    }
  }

  mainPanel.peer.add(gameFieldPanel.peer)
  mainPanel.peer.add(statsPanel.peer)

  contents = mainPanel

  centerOnScreen
  resizable = false
  visible = true

  def drawPlayer(g: Graphics2D, player: Player) {

    val ahead = Vec2(1, 0).rotate(player.direction)
    val pos = Vec2(player.pos.x / factor, player.pos.y / factor)
    val posPeak = pos + ahead * (lineLength / 2)

    val aheadLeft = Vec2(1, 0).rotate((player.direction + sin(60) + Pi).floatValue)
    val aheadRight = Vec2(1, 0).rotate((player.direction - sin(60) + Pi).floatValue)

    val posLeft = posPeak + aheadLeft * lineLength
    val posRight = posPeak + aheadRight * lineLength

    val x1 = posPeak.x.toInt
    val y1 = posPeak.y.toInt

    val x2 = posRight.x.toInt
    val y2 = posRight.y.toInt

    val x3 = posLeft.x.toInt
    val y3 = posLeft.y.toInt

    val oldFont = g.getFont

    g.setColor(Color.YELLOW)
    g.setFont(new Font("Arial", Font.PLAIN, 20))
    g.drawString(currentNames.get(player.publicId).getOrElse(player.publicId + ""), x1 + 20, y1 + 20)

    g.setColor(Color.GREEN)
    g.setFont(oldFont)
    g.drawLine(x1, y1, x3, y3)
    g.drawLine(x1, y1, x2, y2)
  }

  def drawShot(g: Graphics2D, shot: Shot) {
    g.setColor(Color.RED)
    val shotSize = shot.radius / factor
    g.fillOval(shot.pos.x.toInt / factor - (shotSize / 2), shot.pos.y.toInt / factor - (shotSize / 2), shotSize * 2, shotSize * 2)
  }

  def act = {
    loop {
      react {
        case x: World => {
          currentWorld = x
          repaint()
        }

        case x => {
          logger.warn("unknown message received " + x)
        }
      }
    }
  }
}