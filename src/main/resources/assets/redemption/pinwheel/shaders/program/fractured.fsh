#version 150

uniform sampler2D DiffuseSampler;

uniform float ShiftX;
uniform float ShiftY;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec2 uv = texCoord;

    float dirY = (uv.x < 0.5) ? 1.0 : -1.0;
    float dirX = (uv.y < 0.5) ? -1.0 : 1.0;

    uv.y = fract(uv.y + dirY * ShiftY);
    uv.x = fract(uv.x + dirX * ShiftX);

    fragColor = texture(DiffuseSampler, uv);
}
