package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Util;
import br.pucpr.mage.Window;

public class RotatingTriangle implements Scene {
    private Keyboard keys = Keyboard.getInstance();
    private int vao;
	private int positions;
	private int shader;

	@Override
	public void init() {		
		//Define a cor de limpeza da tela
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		//Criação do Vertex Array Object
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		//Atribuição das posições do vértice
		float[] vertexData = new float[] { 
			     0.0f,  0.5f,
			    -0.5f, 0.1f,
			     0.5f, 0.1f,
				0.3f,  -0.6f,
				0.5f, 0.1f,
				-0.3f,  -0.6f,
				 -0.5f, 0.1f,
				 -0.3f,  -0.6f,
				0.5f, 0.1f
		};		
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(vertexData.length);
		positionBuffer.put(vertexData).flip();
		positions = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, positions);
		glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		
		shader = Util.loadProgram("basic.vert", "basic.frag");
		
		//Faxina		
		glBindVertexArray(0);
	}

	@Override
	public void update(float secs) {	
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }	    
	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT);

		//Indica vertex array e o shader object utilizados
		glBindVertexArray(vao);
		glUseProgram(shader);
		
		
		//Associa o buffer "positions" ao atributo do shader "aPosition"
		int aPosition = glGetAttribLocation(shader, "aPosition");
		glEnableVertexAttribArray(aPosition);
		glBindBuffer(GL_ARRAY_BUFFER, positions);				
		glVertexAttribPointer(aPosition, 2, GL_FLOAT, false, 0, 0);
		
		//Comanda o desenho		
		glDrawArrays(GL_TRIANGLES, 0, 9);
		
		//Faxina
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(aPosition);
		glBindVertexArray(0);
		glUseProgram(0);
	}

	@Override
	public void deinit() {
	}

	public static void main(String[] args) {
		new Window(new RotatingTriangle()).show();
	}
}
