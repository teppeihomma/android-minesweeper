package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fernandocejas.android10.sample.domain.GameBoard;
import com.fernandocejas.android10.sample.presentation.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GameBoardAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private GameBoard gameBoard;

    public GameBoardAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return gameBoard != null ? gameBoard.getHeight() * gameBoard.getWidth() : 0;
    }

    @Override
    public Object getItem(int position) {
        int x = position / gameBoard.getWidth();
        int y = position % gameBoard.getHeight();
        Item item = new Item();
        item.cell = gameBoard.getField()[x][y];
        item.cellStatus = gameBoard.getFieldStatuses()[x][y];
        return item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_item_cell, null);
            holder = new ViewHolder();
            ButterKnife.bind(holder, convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = (Item) getItem(position);
        String text = "";
        if (item.cellStatus == GameBoard.FieldStatus.CLOSE) {
            text = "□";
        }
        else if (item.cellStatus == GameBoard.FieldStatus.FLAG) {
            text = "🚩";
        }
        else if (item.cellStatus == GameBoard.FieldStatus.OPENED) {
            if (item.cell == -1) {
                text = "💣";
            }
            else if (item.cell == 0) {
                text = "";
            }
            else {
                text = String.valueOf(item.cell);
            }
        }
        holder.textView.setText(text);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.text_view)
        TextView textView;
    }

    private static class Item {
        int cell;
        int cellStatus;
    }
}
