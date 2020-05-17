package com.mapx.kosten.mosimpa.presentation.activities.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity
import kotlinx.android.synthetic.main.layout_server_item.view.*

class ServersAdapter constructor(
    private val onServerSelected: (ServerEntity, View) -> Unit,
    private val onServerDeleted: (ServerEntity, View) -> Unit
) : RecyclerView.Adapter<ServersAdapter.ServerCellViewHolder>() {

    private var servers: List<ServerEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_server_item,
            parent,
            false)
        return ServerCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return servers.size
    }

    override fun onBindViewHolder(holder: ServerCellViewHolder, position: Int) {
        val server = servers[position]
        holder.bind(server, onServerSelected, onServerDeleted)
    }

    fun setServers(servers: List<ServerEntity>) {
        this.servers = servers
        notifyDataSetChanged()
    }

    class ServerCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            server: ServerEntity,
            selectedItem: (ServerEntity, View) -> Unit,
            deletedItem: (ServerEntity, View) -> Unit) = with(itemView) {
            tv_server_item_name.text = server.name
            tv_server_item_ip.text = server.ip

            tv_server_item_name.setOnClickListener { selectedItem(server, itemView) }
            iv_server_item_delete.setOnClickListener { deletedItem(server, itemView) }
        }
    }
}