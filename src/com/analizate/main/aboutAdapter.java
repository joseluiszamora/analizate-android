package com.analizate.main;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class aboutAdapter extends Activity {
	
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus_fragment_about);
		TextView valuesText = (TextView) findViewById(R.id.textView2xxy);
		
		String values = "<h2>Sobre Nosotros</h2> Somos una institución en salud de diagnostico clinico laboratorial que se encarga de realizar una variada gamma de exámenes, con diferentes grados de complejidad, utilizando para ello muestras de sangre, orina y otros liquidos biologicos.<br><br>Somos un equipo de personas calificadas para brindarle un importante trato humano y un resultado laboratorial confiable con alta utilidad medica, apoyados en el uso de equipos de alta tecnologia cientifica.<br><br>Contamos con un gran espiritu solidario, porque sabemos que nos debemos a nuestra sociedad y porque tomamos muy en cuenta el dolor humano."; 
		valuesText.setText((Html.fromHtml(values)));
	}
}