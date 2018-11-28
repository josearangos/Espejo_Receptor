package com.example.josearangos.espejo_receptor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


public class contentLoading extends Fragment {

    public WebView webView;
    public String url;
    public ProgressBar progressBar;

    public contentLoading() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_content_loading, container, false);
        webView = (WebView) view.findViewById(R.id.webViewPrincipal);
        progressBar = (ProgressBar)view.findViewById(R.id.idProgressBarAplicaciones);

        //http://192.168.0.104:8080/
        url="http://";

        SharedPreferences configuraciones = getActivity().getSharedPreferences("configuraciones", Context.MODE_PRIVATE);

        if(configuraciones.contains("IP")){

            url=url+configuraciones.getString("IP","")+":"+configuraciones.getString("HOST","")+"/";

            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    progressBar.setProgress(0);
                    progressBar.setVisibility(View.VISIBLE);
                    getActivity().setProgress(newProgress*1000);
                    progressBar.incrementProgressBy(newProgress);
                    if(newProgress == 100){
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    //Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setCancelable(false);


                    builder.setTitle("Información importante");
                    builder.setMessage("Pueden existir problemas con los certificados del sitio.");

                    builder.setPositiveButton("continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            handler.proceed();
                        }
                    });
                    builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            handler.cancel();
                        }
                    });
                    builder.create().show();
                }
            });


            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setDomStorageEnabled(true);
            webView.loadUrl(url);



        }else{

            Toast.makeText(getActivity().getBaseContext(),"Debes primero configurar la IP y HOST",Toast.LENGTH_LONG).show();
        }


        return view;
    }

}
