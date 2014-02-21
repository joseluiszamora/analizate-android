package com.analizate.main;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

public class AboutVision extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus_fragment_vision);
		
		TextView misionText = (TextView) findViewById(R.id.textView3xx);
		TextView visionText = (TextView) findViewById(R.id.textView1xx);
		TextView valuesText = (TextView) findViewById(R.id.textView2xxy);
		
		String mision = "<h2>Misión</h2> Ser un aporte positivo a nuestra sociedad, brindando un servicio de diagnostico laboratorial de alta calidad y utilidad medica y gran beneficio a nuestros pacientes. <br><br> Para este cometido contamos con cuatro pilares fundamentales como son la resposabilidad, la eficacia, la calidad y la calidez.";
		String vision = "<h2>Visión</h2>  Llegar a consagrarnos en una institución respetable y confiable de apoyo diagnostico laboratorial, satisfechos de haber contribuido al desarrollo en salud hospitalaria de nuestra sociedad. <br><br> Ser reconocido sobre todo por nuestra actitud profesional y humana.";
		String values = "<h3>Valores</h3>Honestidad<br>Confiabilidad<br>Respeto<br>Experiencia<br>Espiritu de servicio<br>Trabajo en equipo<br>Responsabilidad<br>"; 
		
		misionText.setText((Html.fromHtml(mision)));
		visionText.setText((Html.fromHtml(vision)));
		valuesText.setText((Html.fromHtml(values)));
	}
}