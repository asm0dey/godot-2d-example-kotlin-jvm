package com.github.asm0dey.examplegame

import godot.*
import godot.annotation.Export
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.annotation.RegisterProperty
import godot.core.Vector2
import godot.core.asCachedNodePath
import godot.core.asCachedStringName
import godot.coroutines.await
import godot.coroutines.godotCoroutine
import godot.extensions.getNodeAs
import godot.global.GD
import godot.util.PI
import godot.util.toRealT

@RegisterClass("MainScn")
class Main : Node() {

    @Export
    @RegisterProperty
    var mobScene: PackedScene = PackedScene()

    private var score = 0

    // Called when the node enters the scene tree for the first time.
    @RegisterFunction
    override fun _ready() {
//        newGame()
    }

    // Called every frame. 'delta' is the elapsed time since the previous frame.
    @RegisterFunction
    override fun _process(delta: Double) {

    }

    @RegisterFunction
    fun gameOver() {
        getNodeAs<Timer>("MobTimer")?.stop()
        getNodeAs<Timer>("ScoreTimer")?.stop()
        getNodeAs<Hud>("HUD")?.showGameOver()
    }

    @RegisterFunction
    fun newGame() {
        getTree()!!.callGroup("mobs".asCachedStringName(), "queue_free".asCachedStringName())
        score = 0
        val player = getNodeAs<Player>("Player")!!
        val startPosition = getNodeAs<Marker2D>("StartPosition")!!
        player.start(startPosition.position)
        getNodeAs<Timer>("StartTimer")?.start()
        val hud = getNodeAs<Hud>("HUD")!!
        hud.updateScore(score)
        hud.showMessage("Get Ready!")
    }

    @RegisterFunction
    fun onMobTimerTimeout() {
        val mob = mobScene.instantiate() as Mob
        val mobSpawnLocation = getNodeAs<PathFollow2D>("MobPath/MobSpawnLocation".asCachedNodePath())!!
        mobSpawnLocation.progressRatio = GD.randf()
        mob.position = mobSpawnLocation.position
        val floatPi = PI.toFloat()
        val rotation = mobSpawnLocation.rotation + floatPi / 2 + GD.randfRange(-floatPi / 4, floatPi / 4)
        mob.rotation =
            rotation
        val velocity = Vector2(GD.randfRange(150f, 250f), 0)
        mob.linearVelocity = velocity.rotated(rotation.toRealT())
        addChild(mob)
    }

    @RegisterFunction
    fun onScoreTimerTimeout() {
        score++
        getNodeAs<Hud>("HUD")?.updateScore(score)
    }

    @RegisterFunction
    fun onStartTimerTimeout() {
        getNodeAs<Timer>("MobTimer")?.start()
        getNodeAs<Timer>("ScoreTimer")?.start()
    }

}
