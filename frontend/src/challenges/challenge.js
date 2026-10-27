import { useEffect, useState } from "react";

export default function Challenge({ challengeId }) {
    const [ranking, setRanking] = useState([]);

    useEffect(() => {
        fetch(`api/v1/challenges/${challengeId}/ranking`)
            .then(response => response.json())
            .then(data => setRanking(data));
    }, [challengeId]);

    const leader = ranking.length > 0 ? ranking[0] : null;

    return (
        <div>
            <h1>Challenge info</h1>
            <h3>Ranking</h3>
            {leader && (
                <div>Leader: {leader.user.username} ({leader.points} points)</div>
            )}
        </div>
    );
}
