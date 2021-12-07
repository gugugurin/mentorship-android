package org.anitab.mentorship.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.anitab.mentorship.models.Relationship
import org.anitab.mentorship.remote.datamanager.RelationDataManager
import org.anitab.mentorship.utils.CommonUtils

/**
 * This class represents the [ViewModel] used for Requests Screen
 */
class RequestsViewModel : ViewModel() {

    var tag = RequestsViewModel::class.java.simpleName

    private val relationDataManager = RelationDataManager()

    val successful: MutableLiveData<Boolean> = MutableLiveData()
    val pendingSuccessful: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var pendingAllRequestsList: List<Relationship>

    var message: String? = null
    var allRequestsList: List<Relationship>? = null
    var pastRequestsList: List<Relationship>? = null

    /**
     * Fetches list of all Mentorship relations and requests
     */
    fun getAllMentorshipRelations() {
        viewModelScope.launch {
            try {
                allRequestsList = relationDataManager.getAllRelationsAndRequests().sortedByDescending { it.creationDate }
                successful.postValue(true)
            } catch (throwable: Throwable) {
                message = CommonUtils.getErrorMessage(throwable, tag)
                successful.postValue(false)
            }
        }
    }

    /**
     * Fetches list of all pending Mentorship relations and requests
     */
    fun getAllPendingMentorshipRelations() {
        viewModelScope.launch {
            try {
                pendingAllRequestsList = relationDataManager.getAllPendingRelationsAndRequests().sortedByDescending { it.creationDate }
                pendingSuccessful.postValue(true)
            } catch (throwable: Throwable) {
                message = CommonUtils.getErrorMessage(throwable, tag)
                pendingSuccessful.postValue(false)
            }
        }
    }

    fun getPastMentorshipRelations() {
        viewModelScope.launch {
            try {
                pastRequestsList = relationDataManager.getPastRelationships().sortedByDescending { it.creationDate }
                successful.postValue(true)
            } catch (throwable: Throwable) {
                message = CommonUtils.getErrorMessage(throwable, tag)
                successful.postValue(false)
            }
        }
    }
}
