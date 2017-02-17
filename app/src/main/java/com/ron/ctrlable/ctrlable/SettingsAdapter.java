package com.ron.ctrlable.ctrlable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Android Developer on 2/13/2017.
 */

class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private Context context;
    private int[] buttonResourceArray = new int[]
            {R.drawable.config_app,
            R.drawable.config_vera,
            R.drawable.config_network,
            R.drawable.config_files,
            R.drawable.config_colors,
            R.drawable.config_media,
            R.drawable.config_info,
            R.drawable.config_close};
    private String[] buttonTitleArray = new String[]
            {"General", "Ctrlable", "Network", "Files", "Colors", "Media", "Info", "Close"};

    SettingsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_settings_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SettingsAdapter.ViewHolder holder, final int position) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        if (!ConfigurationClass.isTablet(context)) {
            switch (position%4) {
                case 0:
                    params.setMargins(0, 0, 45, 0);
                    break;
                case 1:
                    params.setMargins(15, 0, 30, 0);
                    break;
                case 2:
                    params.setMargins(30, 0, 15, 0);
                    break;
                case 3:
                    params.setMargins(45, 0, 0, 0);
                    break;
                default:
                    break;
            }
        }

        holder.buttonsView.setLayoutParams(params);
        holder.buttonImage.setBackgroundResource(buttonResourceArray[position]);
        holder.buttonTitlte.setText(buttonTitleArray[position]);

        holder.buttonsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        ((SettingsActivity)context).onGeneral(v);
                        break;
                    case 1:
                        ((SettingsActivity)context).onVera(v);
                        break;
                    case 2:
                        ((SettingsActivity)context).onNetwork(v);
                        break;
                    case 3:
                        ((SettingsActivity)context).onFiles(v);
                        break;
                    case 4:
                        ((SettingsActivity)context).onColors(v);
                        break;
                    case 5:
                        ((SettingsActivity)context).onMedia(v);
                        break;
                    case 6:
                        ((SettingsActivity)context).onInfo(v);
                        break;
                    case 7:
                        ((SettingsActivity)context).onClose(v);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout buttonsView;
        private ImageView buttonImage;
        private TextView buttonTitlte;

        ViewHolder(View itemView) {
            super(itemView);

            buttonsView = (RelativeLayout) itemView.findViewById(R.id.button_view);
            buttonImage = (ImageView) itemView.findViewById(R.id.button_image);
            buttonTitlte = (TextView) itemView.findViewById(R.id.button_title);
        }
    }
}
