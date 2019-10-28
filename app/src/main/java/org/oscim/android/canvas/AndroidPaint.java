/*
 * Copyright 2010, 2011, 2012 mapsforge.org
 * Copyright 2016-2017 devemux86
 * Copyright 2017 nebular
 * Copyright 2018 Gustl22
 *
 * This file is part of the OpenScienceMap project (http://www.opensciencemap.org).
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.oscim.android.canvas;

import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;

import org.oscim.backend.canvas.Paint;

public class AndroidPaint implements Paint {

    private static Typeface getTypeface(org.oscim.backend.canvas.Paint.FontFamily fontFamily,
                                        org.oscim.backend.canvas.Paint.FontStyle fontStyle) {
        final int style;
        switch (fontStyle) {
            case BOLD:
                style = Typeface.BOLD;
                break;
            case BOLD_ITALIC:
                style = Typeface.BOLD_ITALIC;
                break;
            case ITALIC:
                style = Typeface.ITALIC;
                break;
            default:
                style = Typeface.NORMAL;
                break;
        }

        switch (fontFamily) {
            case DEFAULT:
                return Typeface.create(Typeface.DEFAULT, style);
            case DEFAULT_BOLD:
                return Typeface.create(Typeface.DEFAULT_BOLD, style);
            case MONOSPACE:
                return Typeface.create(Typeface.MONOSPACE, style);
            case SANS_SERIF:
                return Typeface.create(Typeface.SANS_SERIF, style);
            case SERIF:
                return Typeface.create(Typeface.SERIF, style);
            case THIN:
                return Typeface.create("sans-serif-thin", style);
            case LIGHT:
                return Typeface.create("sans-serif-light", style);
            case MEDIUM:
                return Typeface.create("sans-serif-medium", style);
            case BLACK:
                return Typeface.create("sans-serif-black", style);
            case CONDENSED:
                return Typeface.create("sans-serif-condensed", style);
        }

        throw new IllegalArgumentException("unknown font family: " + fontFamily);
    }

    final android.graphics.Paint mPaint;

    private final Rect rect = new Rect();

    AndroidPaint() {
        mPaint = new android.graphics.Paint(
                android.graphics.Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public int getColor() {
        return mPaint.getColor();
    }

    @Override
    public void setColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    public void setStrokeCap(Cap cap) {
        android.graphics.Paint.Cap androidCap = android.graphics.Paint.Cap.valueOf(cap.name());
        mPaint.setStrokeCap(androidCap);
    }

    @Override
    public void setStrokeJoin(Join join) {
        android.graphics.Paint.Join androidJoin = android.graphics.Paint.Join.valueOf(join.name());
        mPaint.setStrokeJoin(androidJoin);
    }

    @Override
    public void setStrokeWidth(float width) {
        mPaint.setStrokeWidth(width);
    }

    @Override
    public void setStyle(Style style) {
        mPaint.setStyle(android.graphics.Paint.Style.valueOf(style.name()));
    }

    @Override
    public void setTextAlign(Align align) {
        mPaint.setTextAlign(android.graphics.Paint.Align.valueOf(align.name()));
    }

    @Override
    public void setTextSize(float textSize) {
        mPaint.setTextSize(textSize);
    }

    @Override
    public void setTypeface(FontFamily fontFamily, FontStyle fontStyle) {
        mPaint.setTypeface(getTypeface(fontFamily, fontStyle));
    }

    @Override
    public float measureText(String text) {
        return mPaint.measureText(text);
    }

    @Override
    public int breakText(String text, int start, int end, int maxWidth) {
        return mPaint.breakText(text, start, end, true, maxWidth, null);
    }

    @Override
    public float getFontHeight() {
        FontMetrics fm = mPaint.getFontMetrics();
        return (float) Math.ceil(Math.abs(fm.bottom) + Math.abs(fm.top));
    }

    @Override
    public float getFontDescent() {
        FontMetrics fm = mPaint.getFontMetrics();
        // //fontDescent = (float) Math.ceil(Math.abs(fm.descent));
        return Math.abs(fm.bottom);
    }

    @Override
    public float getStrokeWidth() {
        return mPaint.getStrokeWidth();
    }

    @Override
    public Style getStyle() {
        switch (mPaint.getStyle()) {
            case STROKE:
                return Style.STROKE;
            default:
                return Style.FILL;
        }
    }

    @Override
    public float getTextHeight(String text) {
        mPaint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    @Override
    public float getTextWidth(String text) {
        return measureText(text);
    }

    public android.graphics.Paint getPaint() {
        return mPaint;
    }
}
