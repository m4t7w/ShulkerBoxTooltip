package com.misterpemodder.shulkerboxtooltip.impl.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class VanillaPreviewRenderer extends BasePreviewRenderer {
  public static final Identifier DEFAULT_TEXTURE = new Identifier("textures/gui/container/bundle.png");
  public static final VanillaPreviewRenderer INSTANCE = new VanillaPreviewRenderer();

  VanillaPreviewRenderer() {
    super(18, 20, 2, 2);
  }

  @Override
  public int getWidth() {
    return this.getMaxRowSize() * 18 + 2;
  }

  @Override
  public int getHeight() {
    return this.getRowCount() * 20 + 3;
  }

  private int getColumnCount() {
    return Math.min(this.getMaxRowSize(), this.getInvSize());
  }

  private int getRowCount() {
    return (int) Math.ceil(((double) getInvSize()) / (double) this.getMaxRowSize());
  }

  @Override
  public void draw(int x, int y, DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {
    ++y;
    setTexture();
    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    RenderSystem.enableDepthTest();
    this.drawBackground(x, y, this.getColumnCount(), this.getRowCount(), context);
    this.drawItems(x, y, context, textRenderer);
    this.drawInnerTooltip(x, y, context, textRenderer, mouseX, mouseY);
  }

  private void drawBackground(int x, int y, int columns, int rows, DrawContext context) {
    Identifier texture = this.getTexture();

    for (int row = 0; row < rows; ++row) {
      for (int col = 0; col < columns; ++col) {
        this.drawSprite(context, 1 + x + 18 * col, 1 + y + 20 * row, texture, BundleTooltipComponent.Sprite.SLOT);
      }
    }
    this.drawSprite(context, x, y, texture, BundleTooltipComponent.Sprite.BORDER_CORNER_TOP);
    this.drawSprite(context, x + columns * 18 + 1, y, texture, BundleTooltipComponent.Sprite.BORDER_CORNER_TOP);
    for (int col = 0; col < columns; ++col) {
      this.drawSprite(context, x + 1 + col * 18, y, texture, BundleTooltipComponent.Sprite.BORDER_HORIZONTAL_TOP);
      this.drawSprite(context, x + 1 + col * 18, y + rows * 20, texture,
          BundleTooltipComponent.Sprite.BORDER_HORIZONTAL_BOTTOM);
    }
    for (int row = 0; row < rows; ++row) {
      this.drawSprite(context, x, y + row * 20 + 1, texture, BundleTooltipComponent.Sprite.BORDER_VERTICAL);
      this.drawSprite(context, x + columns * 18 + 1, y + row * 20 + 1, texture,
          BundleTooltipComponent.Sprite.BORDER_VERTICAL);
    }
    this.drawSprite(context, x, y + rows * 20, texture, BundleTooltipComponent.Sprite.BORDER_CORNER_BOTTOM);
    this.drawSprite(context, x + columns * 18 + 1, y + rows * 20, texture,
        BundleTooltipComponent.Sprite.BORDER_CORNER_BOTTOM);
  }

  private void setTexture() {
    if (this.textureOverride == null)
      RenderSystem.setShaderTexture(0, DEFAULT_TEXTURE);
    else
      RenderSystem.setShaderTexture(0, this.textureOverride);
  }

  private Identifier getTexture() {
    if (this.textureOverride == null)
      return DEFAULT_TEXTURE;
    else
      return this.textureOverride;
  }

  private void drawSprite(DrawContext context, int x, int y, Identifier texture, BundleTooltipComponent.Sprite sprite) {
    context.drawTexture(texture, x, y, 0, sprite.u, sprite.v, sprite.width, sprite.height, 128, 128);
  }
}
