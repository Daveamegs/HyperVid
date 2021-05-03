package com.foreverrafs.hypervid.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.foreverrafs.hypervid.R
import com.foreverrafs.hypervid.databinding.FragmentVideosBinding
import com.foreverrafs.hypervid.databinding.ListEmptyBinding
import com.foreverrafs.hypervid.model.FBVideo
import com.foreverrafs.hypervid.ui.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import invisible
import visible
import java.io.File


class VideosFragment : Fragment(), VideoAdapter.VideoCallback {
    private val vm: MainViewModel by activityViewModels()
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var videoBinding: FragmentVideosBinding
    private lateinit var emptyListBinding: ListEmptyBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        videoBinding = FragmentVideosBinding.inflate(inflater)
        emptyListBinding = videoBinding.emptyLayout

        return videoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoAdapter = VideoAdapter(requireActivity().applicationContext, this)

        videoBinding.videoListRecyclerView.adapter =
            videoAdapter


        initEmptyLayoutTexts()

        vm.videosList.observe(viewLifecycleOwner) { videosList ->
            if (videosList.isNotEmpty()) {
                videoBinding.videoListRecyclerView.visible()
                emptyListBinding.root.invisible()

                videoAdapter.submitList(videosList)

            } else {
                videoBinding.videoListRecyclerView.invisible()
                emptyListBinding.root.visible()
            }
        }
    }

    private fun initEmptyLayoutTexts() {
        emptyListBinding.apply {
            tvDescription.text = getString(R.string.empty_video_desc)
            tvTitle.text = getString(R.string.empty_video)
        }
    }

    override fun deleteVideo(video: FBVideo) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.title_delete_video)
            .setIcon(R.drawable.ic_delete)
            .setMessage(R.string.prompt_delete_video)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                vm.deleteVideo(video)
                File(video.path).delete()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                videoAdapter.notifyDataSetChanged()
            }
            .show()
    }
}