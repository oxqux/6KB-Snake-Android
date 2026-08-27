package dev.oxqux.snake;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;
public final class GameView extends View {
    public final Paint a; public final int[] b; public final int[] c;
    public int d; public int e; public int f; public int g;
    public final int h; public int i; public int j; public boolean k;
    public final Random l; public float m; public float n;
    public GameView(Context x){ super(x); a=new Paint(); b=new int[3000]; c=new int[3000]; g=1; h=40; k=false; l=new Random(); a.setAntiAlias(true); reset(); }
    public final void reset(){ d=3; b[0]=5; c[0]=5; b[1]=4; c[1]=5; b[2]=3; c[2]=5; g=1; k=false; spawnFood(); postInvalidate(); }
    public final void spawnFood(){ if(i==0||j==0) return; while(true){ e=l.nextInt(i); f=l.nextInt(j); boolean hit=false; for(int x=0;x<d;x++){ if(b[x]==e&&c[x]==f){ hit=true; break; } } if(!hit) return; } }
    @Override public final void onDraw(Canvas cv){ super.onDraw(cv); cv.drawColor(0xff000000); if(k){ a.setColor(0xffff0000); a.setTextSize(100.0f); a.setTextAlign(Paint.Align.CENTER); cv.drawText("GAME OVER",getWidth()/2.0f,getHeight()/2.0f,a); a.setTextSize(50.0f); a.setColor(0xffffffff); cv.drawText("Tap to Restart",getWidth()/2.0f,getHeight()/2.0f+100.0f,a); return; } for(int v=d-1;v>0;v--){ b[v]=b[v-1]; c[v]=c[v-1]; } if(g==0) c[0]-=1; else if(g==1) b[0]+=1; else if(g==2) c[0]+=1; else if(g==3) b[0]-=1; if(b[0]<0||b[0]>=i||c[0]<0||c[0]>=j) k=true; for(int v=1;v<d;v++){ if(b[0]==b[v]&&c[0]==c[v]) k=true; } if(k){ postInvalidate(); return; } if(b[0]==e&&c[0]==f){ d=d+1; b[d]=b[d-1]; c[d]=c[d-1]; spawnFood(); } a.setColor(0xff00ff00); cv.drawRect(e*h,f*h,(e+1)*h,(f+1)*h,a); a.setColor(0xffff0000); for(int v=0;v<d;v++){ cv.drawRect(b[v]*h,c[v]*h,(b[v]+1)*h,(c[v]+1)*h,a); } a.setColor(0xffffffff); a.setTextSize(50.0f); a.setTextAlign(Paint.Align.LEFT); cv.drawText("Score: "+(d-3),50.0f,80.0f,a); postInvalidateDelayed(100); }
    @Override public final void onSizeChanged(int w,int h2,int ow,int oh){ super.onSizeChanged(w,h2,ow,oh); i=w/h; j=h2/h; spawnFood(); }
    @Override public final boolean onTouchEvent(MotionEvent ev){ int ac=ev.getAction(); if(ac==0){ if(k){ reset(); return true; } m=ev.getX(); n=ev.getY(); } else if(ac==1){ float dx=ev.getX()-m; float dy=ev.getY()-n; if(Math.abs(dx)>Math.abs(dy)){ if(Math.abs(dx)>50.0f){ if(dx>0.0f){ if(g!=3) g=1; } else { if(g!=1) g=3; } } } else { if(Math.abs(dy)>50.0f){ if(dy>0.0f){ if(g!=0) g=2; } else { if(g!=2) g=0; } } } } return true; }
}
