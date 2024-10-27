package com.github.asm0dey.examplegame

import godot.*
import godot.annotation.*
import godot.core.*
import godot.extensions.getNodeAs
import godot.global.GD

@RegisterClass
class Player : Area2D() {

	// Declare member variables here. Examples:
	// val a = 2
	// val b = "text"

	@Export
	@RegisterProperty
	var speed = 400

	var screenSize = Vector2()

	@RegisterSignal
	val hit by signal0()

	// Called when the node enters the scene tree for the first time.
	@RegisterFunction
	override fun _ready() {
		screenSize = getViewport()?.getVisibleRect()?.size!!
		hide()
	}

	// Called every frame. 'delta' is the elapsed time since the previous frame.
	@RegisterFunction
	override fun _process(delta: Double) {
		var velocity = Vector2.ZERO
		if (Input.isActionPressed("move_up".asCachedStringName())) velocity += Vector2.UP
		if (Input.isActionPressed("move_down".asCachedStringName())) velocity += Vector2.DOWN
		if (Input.isActionPressed("move_left".asCachedStringName())) velocity += Vector2.LEFT
		if (Input.isActionPressed("move_right".asCachedStringName())) velocity += Vector2.RIGHT

		val animatedSprite2D = getNodeAs<AnimatedSprite2D>("AnimatedSprite2D")!!
		if (velocity.length() > 0) {
			velocity = velocity.normalized() * speed
			animatedSprite2D.play()
		} else
			animatedSprite2D.stop()
		position += velocity * delta
		position = position.clamp(Vector2.ZERO, screenSize)
		if (velocity.x != 0.0) {
			animatedSprite2D.animation = "walk".asCachedStringName()
			animatedSprite2D.flipV = false
			animatedSprite2D.flipH = velocity.x < 0
		} else if (velocity.y != 0.0) {
			animatedSprite2D.animation = "up".asCachedStringName()
			animatedSprite2D.flipV = velocity.y > 0
		}
	}

	@RegisterFunction
	fun onBodyEntered(body: Node2D) {
		hide()
		hit.emit()
		getNodeAs<CollisionShape2D>("CollisionShape2D".asCachedNodePath())
			?.setDeferred(
				"disabled".asCachedStringName(),
				true
			)
	}

	@RegisterFunction
	fun start(pos: Vector2) {
		position = pos
		show()
		getNodeAs<CollisionShape2D>("CollisionShape2D".asCachedNodePath())?.disabled = false
	}
}
