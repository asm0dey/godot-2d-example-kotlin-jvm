package com.github.asm0dey.examplegame

import godot.AnimatedSprite2D
import godot.RigidBody2D
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.PackedStringArray
import godot.core.asCachedStringName
import godot.extensions.getNodeAs
import godot.global.GD
import godot.rem

@RegisterClass
class Mob : RigidBody2D() {

	// Declare member variables here. Examples:
	// val a = 2
	// val b = "text"

	// Called when the node enters the scene tree for the first time.
	@RegisterFunction
	override fun _ready() {
		val animatedSprite2D = getNodeAs<AnimatedSprite2D>("AnimatedSprite2D")!!
		val mobTypes = animatedSprite2D.spriteFrames?.getAnimationNames()!!
		animatedSprite2D.play(mobTypes.random())
	}

	private fun PackedStringArray.random() =
		this[(GD.randi() % size).toInt()].asCachedStringName()

	// Called every frame. 'delta' is the elapsed time since the previous frame.
	@RegisterFunction
	override fun _process(delta: Double) {

	}

	@RegisterFunction
	fun onExit(){
		queueFree()
	}
}
