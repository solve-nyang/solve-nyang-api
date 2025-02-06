package com.ssafy.solvedpick.common.enums;

import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LetterType {

    A('A'), B('B'), C('C'), D('D'), E('E'),
    F('F'), G('G'), H('H'), I('I'), J('J'),
    K('K'), L('L'), M('M'), N('N'), O('O'),
    P('P'), Q('Q'), R('R'), S('S'), T('T'),
    U('U'), V('V'), W('W'), X('X'), Y('Y'),
    Z('Z'),

    a('a'), b('b'), c('c'), d('d'), e('e'),
    f('f'), g('g'), h('h'), i('i'), j('j'),
    k('k'), l('l'), m('m'), n('n'), o('o'),
    p('p'), q('q'), r('r'), s('s'), t('t'),
    u('u'), v('v'), w('w'), x('x'), y('y'),
    z('z');

    private final char letter;

    public String getSvgContent(SvgResources svgResources) {
        return svgResources.getLetters().get(this.letter);
    }


}
