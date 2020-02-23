package com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.state

sealed class SkillsStateEvent{

    class GetSkillsListStateEvent(): SkillsStateEvent()

    class None: SkillsStateEvent()
}