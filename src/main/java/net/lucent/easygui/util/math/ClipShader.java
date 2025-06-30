package net.lucent.easygui.util.math;

public class ClipShader {

    private static final String FRAGMENT_SHADER_SOURCE = """
    #version 150

    uniform sampler2D Sampler0;
    uniform vec2 u_CullRegion[4];

    in vec2 texCoord;
    out vec4 fragColor;

    bool pointInQuad(vec2 p) {
        for (int i = 0; i < 4; i++) {
            vec2 a = u_CullRegion[i];
            vec2 b = u_CullRegion[(i + 1) % 4];
            vec2 edge = b - a;
            vec2 toPoint = p - a;

            float cross = edge.x * toPoint.y - edge.y * toPoint.x;
            if (cross < 0.0) return false;
        }
        return true;
    }

    void main() {
        if (!pointInQuad(gl_FragCoord.xy)) {
            discard;
        }

        fragColor = texture(Sampler0, texCoord);
    }
    """;
}
