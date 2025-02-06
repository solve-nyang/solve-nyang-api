package com.ssafy.solvedpick.common.enums;

import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LetterType {
    A('A', 13, 0), B('B', 13, 0), C('C', 13, 0), D('D', 13, 0), E('E', 13, 0),
    F('F', 11, 0), G('G', 13, 0), H('H', 13, 0), I('I', 8, 0), J('J', 13, 0),
    K('K', 13, 0), L('L', 10, 0), M('M', 15, 0), N('N', 13, 0), O('O', 12, 0),
    P('P', 13, 0), Q('Q', 13, 0), R('R', 13, 0), S('S', 11, 0), T('T', 13, 0),
    U('U', 12, 0), V('V', 13, 0), W('W', 19, 0), X('X', 13, 0), Y('Y', 13, 0),
    Z('Z', 13, 0),

    a('a', 10, 3), b('b', 13, 0), c('c', 13, 3), d('d', 13, 0), e('e', 12, 3),
    f('f', 10, 0), g('g', 13, 4), h('h', 11, -1), i('i', 5, 0), j('j', 10, 1),
    k('k', 13, 0), l('l', 5, 0), m('m', 16, 3), n('n', 13, 3), o('o', 11, 3),
    p('p', 13, 3), q('q', 13, 3), r('r', 10, 3), s('s', 10, 3), t('t', 10, 0),
    u('u', 13, 3), v('v', 13, 3), w('w', 16, 3), x('x', 13, 3), y('y', 13, 3),
    z('z', 10, 3),

    ONE('1', 8, 0), TWO('2', 13, 0), THREE('3', 13, 0), FOUR('4', 13, 0), FIVE('5', 13, 0),
    SIX('6', 13, 0), SEVEN('7', 13, 0), EIGHT('8', 13, 0), NINE('9', 13, 0), ZERO('0', 13, 0);

    private final char name;
    private final int gap;
    private final int hgap;

    public String getSvgContent(SvgResources svgResources) {
        return Character.isDigit(this.name)
                ? svgResources.getNumbers().get(this.name)
                : svgResources.getLetters().get(this.name);
    }

    public static LetterType fromName(char name) {
        return Arrays.stream(values())
                .filter(type -> type.getName() == name)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid LetterType: " + name));
    }

}
