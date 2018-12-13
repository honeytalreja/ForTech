package com.fortech.protalendeavours.fortech;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Honey on 08-Oct-18.
 */

public class HomeFragRVAdapter extends RecyclerView.Adapter<HomeFragRVAdapter.ViewHolder>{


    private ArrayList<String> projTitles = new ArrayList<>();
    private ArrayList<String> projDomains = new ArrayList<>();
    private ArrayList<String> projGuides = new ArrayList<>();
    private ArrayList<String> domainExperts = new ArrayList<>();
    private ArrayList<String> projectID = new ArrayList<>();

    private Context mContext;
    private Activity mActivity;
    public HomeFragRVAdapter(Context mContext, Activity mActivity, ArrayList<String> projTitles, ArrayList<String> projDomains, ArrayList<String> projGuides, ArrayList<String> domainExperts, ArrayList<String> projectID) {
        this.projTitles = projTitles;
        this.projDomains = projDomains;
        this.projGuides = projGuides;
        this.domainExperts = domainExperts;
        this.projectID = projectID;
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_frag_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.projectTitle.setText(projTitles.get(position));
        holder.projectDomain.setText(projDomains.get(position));
        holder.projectGuide.setText(projGuides.get(position));
        holder.domainExpert.setText(domainExperts.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,projTitles.get(position),Toast.LENGTH_SHORT).show();
                Intent projectInfoIntent = new Intent(mContext,ShowFTRActivity.class);
                projectInfoIntent.putExtra("currentProjectID",projectID.get(position));
                mActivity.startActivity(projectInfoIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView projectCard;
        TextView projectTitle;
        TextView projectDomain;
        TextView projectGuide;
        TextView domainExpert;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            projectCard = itemView.findViewById(R.id.projectCard);
            projectTitle = itemView.findViewById(R.id.projectTitle);
            projectDomain = itemView.findViewById(R.id.projectDomain);
            projectGuide = itemView.findViewById(R.id.projectGuide);
            domainExpert = itemView.findViewById(R.id.domainExpert);
            parentLayout = itemView.findViewById(R.id.parentLayout);

        }
    }
}
