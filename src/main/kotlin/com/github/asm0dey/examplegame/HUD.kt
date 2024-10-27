package com.github.asm0dey.examplegame

import godot.Button
import godot.CanvasLayer
import godot.Label
import godot.Timer
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.annotation.RegisterSignal
import godot.core.signal0
import godot.coroutines.await
import godot.coroutines.godotCoroutine
import godot.extensions.getNodeAs

@RegisterClass
class Hud : CanvasLayer() {

	// Declare member variables here. Examples:
	// val a = 2
	// val b = "text"
	@RegisterSignal
	val startGame by signal0()

	// Called when the node enters the scene tree for the first time.
	@RegisterFunction
	override fun _ready() {

	}

	// Called every frame. 'delta' is the elapsed time since the previous frame.
	@RegisterFunction
	override fun _process(delta: Double) {

	}

	fun showMessage(text: String) {
		val message = getNodeAs<Label>("Message")!!
		message.text = text
		message.show()

		getNodeAs<Timer>("MessageTimer")!!.start()
	}

	fun updateScore(score: Int) {
		getNodeAs<Label>("ScoreLabel")?.text = score.toString()
	}

	@RegisterFunction
	fun startPressed() {
		getNodeAs<Button>("StartButton")?.hide()
		startGame.emit()
	}

	@RegisterFunction
	fun messageTimerTimeout() {
		getNodeAs<Label>("Message")?.hide()
	}

	fun showGameOver() {
		showMessage("Game Over!")
		val messageTimer = getNodeAs<Timer>("MessageTimer")!!
		godotCoroutine {
			messageTimer.timeout.await()
		}
		val message = getNodeAs<Label>("Message")!!
		message.text = "Dodge the Creeps!"
		message.show()
		godotCoroutine {
			getTree()!!.createTimer(1.0)!!.timeout.await()
		}
		getNodeAs<Button>("StartButton")!!.show()
	}

}
