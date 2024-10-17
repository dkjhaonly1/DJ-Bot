import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deepak.djbot.databinding.ChatrightitemBinding
import com.deepak.djbot.databinding.ChatleftitemBinding

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_RIGHT) {
            val binding = ChatrightitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            RightChatViewHolder(binding)
        } else {
            val binding = ChatleftitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LeftChatViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Binding logic here
    }

    override fun getItemCount(): Int {
        return 0 // Replace with actual item count
    }

    override fun getItemViewType(position: Int): Int {
        // Determine view type based on position
        return VIEW_TYPE_RIGHT // or VIEW_TYPE_LEFT
    }

    class RightChatViewHolder(val binding: ChatrightitemBinding) : RecyclerView.ViewHolder(binding.root)
    class LeftChatViewHolder(val binding: ChatleftitemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val VIEW_TYPE_RIGHT = 1
        const val VIEW_TYPE_LEFT = 2
    }
}
