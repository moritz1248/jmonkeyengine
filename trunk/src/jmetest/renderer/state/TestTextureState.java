/*
 * Copyright (c) 2003-2005 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jmetest.renderer.state;

import com.jme.app.BaseGame;
import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.TextureManager;
import com.jme.util.geom.BufferUtils;

/**
 * <code>TestTextureState</code>
 * @author Mark Powell
 * @version
 */
public class TestTextureState extends BaseGame {
    private TriMesh t, t2;
    private Camera cam;
    private Node scene;
    Vector3f trans;
    Texture texture;
    TextureState ts;

    /**
     * Entry point for the test,
     * @param args
     */
    public static void main(String[] args) {
        TestTextureState app = new TestTextureState();
        app.setDialogBehaviour(ALWAYS_SHOW_PROPS_DIALOG);
        app.start();
    }

    /**
     * Not used in this test.
     * @see com.jme.app.SimpleGame#update()
     */
    protected void update(float interpolation) {
        
        trans.x += 0.0003 * interpolation;
        trans.y += 0.0003 * interpolation;
        
        if(trans.x > 10) {
            trans.x = 0;
        }
        
        if(trans.y > 10) {
            trans.y = 0;
        }
        
        texture.setTranslation(trans);
        ts.setTexture(texture);

    }

    /**
     * clears the buffers and then draws the TriMesh.
     * @see com.jme.app.SimpleGame#render()
     */
    protected void render(float interpolation) {
        display.getRenderer().clearBuffers();

        display.getRenderer().draw(scene);

    }

    /**
     * creates the displays and sets up the viewport.
     * @see com.jme.app.SimpleGame#initSystem()
     */
    protected void initSystem() {
        try {
            display = DisplaySystem.getDisplaySystem(properties.getRenderer());
            display.createWindow(
                properties.getWidth(),
                properties.getHeight(),
                properties.getDepth(),
                properties.getFreq(),
                properties.getFullscreen());
            cam =
                display.getRenderer().createCamera(
                    properties.getWidth(),
                    properties.getHeight());

        } catch (JmeException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ColorRGBA blueColor = new ColorRGBA();
        blueColor.r = 0;
        blueColor.g = 0;
        display.getRenderer().setBackgroundColor(blueColor);
        cam.setFrustum(1.0f, 1000.0f, -0.55f, 0.55f, 0.4125f, -0.4125f);
        Vector3f loc = new Vector3f(4.0f, 0.0f, 0.0f);
        Vector3f left = new Vector3f(0.0f, -1.0f, 0.0f);
        Vector3f up = new Vector3f(0.0f, 0.0f, 1.0f);
        Vector3f dir = new Vector3f(-1.0f, 0f, 0.0f);
        cam.setFrame(loc, left, up, dir);
        display.getRenderer().setCamera(cam);
        
        trans = new Vector3f();

    }

    /**
     * builds the trimesh.
     * @see com.jme.app.SimpleGame#initGame()
     */
    protected void initGame() {
        Vector3f[] verts = new Vector3f[3];
        ColorRGBA[] color = new ColorRGBA[3];
        Vector2f[] tex = new Vector2f[3];

        verts[0] = new Vector3f();
        verts[0].x = -50;
        verts[0].y = 0;
        verts[0].z = 0;
        verts[1] = new Vector3f();
        verts[1].x = -50;
        verts[1].y = 25;
        verts[1].z = 25;
        verts[2] = new Vector3f();
        verts[2].x = -50;
        verts[2].y = 25;
        verts[2].z = 0;

        tex[0] = new Vector2f();
        tex[0].x = 1;
        tex[0].y = 0;
        tex[1] = new Vector2f();
        tex[1].x = 1;
        tex[1].y = 1;
        tex[2] = new Vector2f();
        tex[2].x = 0;
        tex[2].y = 1;

        color[0] = new ColorRGBA();
        color[0].r = 1;
        color[0].g = 0;
        color[0].b = 0;
        color[0].a = 1;
        color[1] = new ColorRGBA();
        color[1].r = 0;
        color[1].g = 1;
        color[1].b = 0;
        color[1].a = 1;
        color[2] = new ColorRGBA();
        color[2].r = 0;
        color[2].g = 0;
        color[2].b = 1;
        color[2].a = 1;
        int[] indices = { 0, 1, 2 };

        t = new TriMesh("Triangle", BufferUtils.createFloatBuffer(verts), null, BufferUtils.createFloatBuffer(color), BufferUtils.createFloatBuffer(tex), BufferUtils.createIntBuffer(indices));
        t.setModelBound(new BoundingSphere());
        t.updateModelBound();

        Vector3f[] verts2 = new Vector3f[3];
        ColorRGBA[] color2 = new ColorRGBA[3];

        verts2[0] = new Vector3f();
        verts2[0].x = -40;
        verts2[0].y = 10;
        verts2[0].z = 0;
        verts2[1] = new Vector3f();
        verts2[1].x = -40;
        verts2[1].y = 35;
        verts2[1].z = 15;
        verts2[2] = new Vector3f();
        verts2[2].x = -10;
        verts2[2].y = 35;
        verts2[2].z = -10;

        color2[0] = new ColorRGBA();
        color2[0].r = 1;
        color2[0].g = 0;
        color2[0].b = 0;
        color2[0].a = 1;
        color2[1] = new ColorRGBA();
        color2[1].r = 0;
        color2[1].g = 1;
        color2[1].b = 0;
        color2[1].a = 1;
        color2[2] = new ColorRGBA();
        color2[2].r = 0;
        color2[2].g = 0;
        color2[2].b = 1;
        color2[2].a = 1;
        int[] indices2 = { 0, 1, 2 };

        t2 = new TriMesh("Triangle 2", BufferUtils.createFloatBuffer(verts2), null, BufferUtils.createFloatBuffer(color2), BufferUtils.createFloatBuffer(tex), BufferUtils.createIntBuffer(indices2));
        t2.setModelBound(new BoundingSphere());
        t2.updateModelBound();
        System.out.println(t.getModelBound());
        cam.update();

        scene = new Node("Scene Node");
        scene.attachChild(t);
        scene.attachChild(t2);
        scene.setLocalTranslation(new Vector3f(0, -25, 0));

        ts = display.getRenderer().createTextureState();
        ts.setEnabled(true);
        
        texture = TextureManager.loadTexture(
                TestTextureState.class.getClassLoader().getResource("jmetest/data/model/marble.bmp"),
                Texture.MM_LINEAR,
                Texture.FM_LINEAR);
        texture.setWrap(Texture.WM_WRAP_S_WRAP_T);
        ts.setTexture(texture);
            
        t2.setRenderState(ts);

        cam.update();

        scene.updateGeometricState(0.0f, true);
        scene.updateRenderState();
    }
    /**
     * not used.
     * @see com.jme.app.SimpleGame#reinit()
     */
    protected void reinit() {

    }

    /**
     * Not used.
     * @see com.jme.app.SimpleGame#cleanup()
     */
    protected void cleanup() {

    }

}
