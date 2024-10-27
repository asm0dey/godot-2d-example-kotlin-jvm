package com.github.asm0dey.examplegame

import godot.Node3D
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.global.GD

@RegisterClass
class Simple: Node3D() {

    @RegisterFunction
    override fun _ready() {
        GD.print("Hello world!")
    }
}
